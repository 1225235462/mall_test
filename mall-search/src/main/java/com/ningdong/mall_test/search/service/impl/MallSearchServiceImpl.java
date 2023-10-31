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
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;

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

        SearchResult searchResult = buildSearchResult(entities);

        return searchResult;
    }

    private Query buildSearchRequest(SearchParam param) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (!ObjectUtils.isEmpty(param.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle",param.getKeyword()));
        }

        if (!ObjectUtils.isEmpty(param.getCatalog3Id())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("catalogId",param.getCatalog3Id()));
        }

        if (!ObjectUtils.isEmpty(param.getBrandId())){
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId",param.getBrandId()));
        }

        if (param.getHasStock() == 1) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock", true));
        }

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

        if (!ObjectUtils.isEmpty(param.getSort())){
            String sortFrom = param.getSort();

            String[] s = sortFrom.split("_");

            Sort.Direction direction = s[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

            builder.withSort(Sort.by(direction,s[0]));
        }

        builder.withPageable(PageRequest.of(param.getPageNum()-1, EsConstant.PRODUCT_PAGESIZE));

        if (!ObjectUtils.isEmpty(param.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("<b>");

            builder.withHighlightBuilder(highlightBuilder);
        }

        return builder.build();
    }

    private SearchResult buildSearchResult(SearchHits<SkuEsEntity> entities) {

        System.out.println(entities.toString());
        return null;
    }
}
