package com.ningdong.mall_test.ware.vo;

import lombok.Data;

@Data
public class PurchaseItemDoneVo {
    //采购需求id
    private Long itemId;

    //采购成功或失败等状态
    private Integer status;

    //原因
    private String reason;
}
