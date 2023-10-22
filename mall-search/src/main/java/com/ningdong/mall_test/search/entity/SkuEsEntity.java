package com.ningdong.mall_test.search.entity;

import com.ningdong.common.to.es.SkuEsModel;
import com.ningdong.mall_test.search.constant.EsConstant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(indexName = EsConstant.PRODUCT_INDEX)
public class SkuEsEntity {
    @Id
    @Field
    private Long skuId;

    @Field
    private Long spuId;

    @Field
    private String skuTitle;

    @Field
    private BigDecimal skuPrice;

    @Field
    private String skuImg;

    @Field
    private Long saleCount;

    @Field
    private Boolean hasStock;

    @Field
    private Long hotScore;

    @Field
    private Long brandId;

    @Field
    private Long catalogId;

    @Field
    private String brandName;

    @Field
    private String brandImg;

    @Field
    private String catalogName;

    @Field
    private List<SkuEsModel.Attrs> attrs;

    @Data
    public static class Attrs{
        @Field
        private Long attrId;

        @Field
        private String attrName;

        @Field
        private String attrValue;
    }
}
