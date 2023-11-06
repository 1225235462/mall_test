package com.ningdong.mall_test.product.vo;

import com.ningdong.mall_test.product.entity.SkuImagesEntity;
import com.ningdong.mall_test.product.entity.SkuInfoEntity;
import com.ningdong.mall_test.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {

    private SkuInfoEntity info;

    private List<SkuImagesEntity> images;

    private List<SkuItemSaleAttrVo> saleAttr;

    private SpuInfoDescEntity desp;

    private List<SpuItemAttrGroupVo> groupAttrs;

    @Data
    public static class SkuItemSaleAttrVo{
        private Long attrId;

        private String attrName;

        private List<String> attrValues;
    }

    @Data
    public static class SpuItemAttrGroupVo{
        private String groupName;

        private List<SpuBaseAttrVo> attrs;
    }

    @Data
    public static class SpuBaseAttrVo{
        private String attrName;

        private String attrValue;
    }
}
