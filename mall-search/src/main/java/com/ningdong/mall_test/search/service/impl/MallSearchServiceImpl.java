package com.ningdong.mall_test.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.ningdong.mall_test.search.constant.EsConstant;
import com.ningdong.mall_test.search.entity.SkuEsEntity;
import com.ningdong.mall_test.search.repository.ProductRepository;
import com.ningdong.mall_test.search.service.MallSearchService;
import com.ningdong.mall_test.search.vo.SearchParam;
import com.ningdong.mall_test.search.vo.SearchResult;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    ProductRepository productRepository;

    @Resource
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchResult search(SearchParam param) {

        Query query = buildSearchRequest(param);

        SearchHits<SkuEsEntity> entities = elasticsearchOperations.search(query, SkuEsEntity.class);

        SearchResult searchResult = buildSearchResult(entities,param);

        return searchResult;
    }

    private Query buildSearchRequest(SearchParam param) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //关键词匹配
        if (!ObjectUtils.isEmpty(param.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle",param.getKeyword()));
        }

        //所属种类匹配
        if (!ObjectUtils.isEmpty(param.getCatalog3Id())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("catalogId",param.getCatalog3Id()));
        }

        //品牌id匹配
        if (!ObjectUtils.isEmpty(param.getBrandId())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId",param.getBrandId()));
        }

        //是否有库存匹配
        if (param.getHasStock() == 1) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock", true));
        }

        //价格区间匹配
        if (!ObjectUtils.isEmpty(param.getSkuPrice())){
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");

            String[] s = param.getSkuPrice().split("_");
            if (s.length == 2){
                if (param.getSkuPrice().startsWith("_")){
                    rangeQuery.lte(s[1]);
                }else {
                    rangeQuery.gte(s[0]).lte(s[1]);
                }
            }else if (s.length == 1){
                rangeQuery.gte(s[0]);
            }

            boolQueryBuilder.filter(rangeQuery);
        }

        //属性匹配
        if (!ObjectUtils.isEmpty(param.getAttrs())){
            for (String attr : param.getAttrs()){
                BoolQueryBuilder nestedQuery = QueryBuilders.boolQuery();
                String[] attrStr = attr.split("_");
                String attrId = attrStr[0];
                attrStr = attrStr[1].split(":");
                nestedQuery.must(QueryBuilders.termQuery("attrs.attrId",attrId));
                nestedQuery.must(QueryBuilders.termsQuery("attrs.attrValue",attrStr));
                NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("attrs",nestedQuery, ScoreMode.None);
                boolQueryBuilder.filter(nestedQueryBuilder);
            }
        }

        builder.withQuery(boolQueryBuilder);

        //排序
        if (!ObjectUtils.isEmpty(param.getSort())){
            String sortFrom = param.getSort();

            String[] s = sortFrom.split("_");

            Sort.Direction direction = s[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

            builder.withSort(Sort.by(direction,s[0]));
        }

        //分页，指定页码和每页展示商品数
        if(!ObjectUtils.isEmpty(param.getPageNum()) && param.getPageNum() > 0) {
            builder.withPageable(PageRequest.of(param.getPageNum() - 1, EsConstant.PRODUCT_PAGESIZE));
        }else {
            builder.withPageable(PageRequest.of(0, EsConstant.PRODUCT_PAGESIZE));
        }

        //关键词高亮
        if (!ObjectUtils.isEmpty(param.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");

            builder.withHighlightBuilder(highlightBuilder);
        }

        //聚合查询
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        brand_agg.field("brandId").size(50);
        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        builder.withAggregations(brand_agg);

        TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        catalog_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName.keyword").size(1));
        builder.withAggregations(catalog_agg);

        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        attr_agg.subAggregation(attr_id_agg);
        builder.withAggregations(attr_agg);

        return builder.build();
    }

    private SearchResult buildSearchResult(SearchHits<SkuEsEntity> entities,SearchParam param) {

//        System.out.println(entities.toString());
//        entities.forEach(System.out::println);

        SearchResult result = new SearchResult();

        //封装分页信息
        result.setPageNum(param.getPageNum());
        long totalHits = entities.getTotalHits();
        result.setTotal(totalHits);
        int totalPages = (int) Math.ceil((double) totalHits/EsConstant.PRODUCT_PAGESIZE);
        result.setTotalPages(totalPages);

        List<SkuEsEntity> esEntities = entities.get().map(hit -> {
            SkuEsEntity entity = hit.getContent();
            if (!ObjectUtils.isEmpty(param.getKeyword())){
                String skuTitle = hit.getHighlightField("skuTitle").get(0);
                entity.setSkuTitle(skuTitle);
            }
            return entity;
        }).collect(Collectors.toList());
        result.setProducts(esEntities);

        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) entities.getAggregations();
//        assert aggregations != null;
//        Map<String, Aggregation> aggregationMap = aggregations.aggregations().asMap();
//
//        ParsedLongTerms catalog_agg = (ParsedLongTerms) aggregationMap.get("catalog_agg");

        assert aggregations != null;
        ParsedLongTerms catalog_agg = aggregations.aggregations().get("catalog_agg");

        ArrayList<SearchResult.CatalogVo> catalogVos = new ArrayList<>();
        List<? extends Terms.Bucket> catalogAggBuckets = catalog_agg.getBuckets();
        for (Terms.Bucket bucket : catalogAggBuckets){
            SearchResult.CatalogVo vo = new SearchResult.CatalogVo();
            vo.setCatalogId((Long) bucket.getKey());

            ParsedStringTerms catalog_name_agg = bucket.getAggregations().get("catalog_name_agg");
            vo.setCatalogName(catalog_name_agg.getBuckets().get(0).getKeyAsString());

            catalogVos.add(vo);
        }
        result.setCatalogs(catalogVos);

        ParsedLongTerms brand_agg = aggregations.aggregations().get("brand_agg");
        ArrayList<SearchResult.BrandVo> brandVos = new ArrayList<>();
        brand_agg.getBuckets().forEach(bucket -> {
            SearchResult.BrandVo vo = new SearchResult.BrandVo();
            vo.setBrandId((Long) bucket.getKey());

            ParsedStringTerms brand_name_agg = bucket.getAggregations().get("brand_name_agg");
            vo.setBrandName(brand_name_agg.getBuckets().get(0).getKeyAsString());

            ParsedStringTerms brand_img_agg = bucket.getAggregations().get("brand_img_agg");
            vo.setBrandImg(brand_img_agg.getBuckets().get(0).getKeyAsString());

            brandVos.add(vo);
        });
        result.setBrands(brandVos);

        ParsedNested attr_agg = aggregations.aggregations().get("attr_agg");
        ParsedLongTerms attr_id_agg = attr_agg.getAggregations().get("attr_id_agg");
        ArrayList<SearchResult.AttrVo> attrVos = new ArrayList<>();
        attr_id_agg.getBuckets().forEach(bucket -> {
            SearchResult.AttrVo vo = new SearchResult.AttrVo();
            vo.setAttrId((Long) bucket.getKey());

            ParsedStringTerms attr_name_agg = bucket.getAggregations().get("attr_name_agg");
            vo.setAttrName(attr_name_agg.getBuckets().get(0).getKeyAsString());

            ParsedStringTerms attr_value_agg = bucket.getAggregations().get("attr_value_agg");
            List<String> attrValues = attr_value_agg.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());
            vo.setAttrValue(attrValues);

            attrVos.add(vo);
        });
        result.setAttrs(attrVos);

        return result;
    }
}
