package com.ningdong.mall_test.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class MergeVo {
    //采购单id
    private Long purchaseId;

    //需要分配的采购需求id列表
    private List<Long> items;
}
