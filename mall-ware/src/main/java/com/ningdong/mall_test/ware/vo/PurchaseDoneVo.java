package com.ningdong.mall_test.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PurchaseDoneVo {
    //采购单id
    @NotNull
    private Long id;

    //采购的商品
    private List<PurchaseItemDoneVo> items;
}
