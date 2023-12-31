package com.ningdong.mall_test.search.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchParam {
    private String keyword;

    private Long catalog3Id;

    private String sort;

    private Integer hasStock = 0;

    private String skuPrice;

    private List<Long> brandId;

    private List<String> attrs;

    private Integer pageNum = 1;
}
