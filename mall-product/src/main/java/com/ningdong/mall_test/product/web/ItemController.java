package com.ningdong.mall_test.product.web;

import com.ningdong.mall_test.product.service.SkuInfoService;
import com.ningdong.mall_test.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {
    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model){
        SkuItemVo vo = skuInfoService.item(skuId);
//        System.out.println(vo);
        model.addAttribute("item",vo);

        return "item";
    }
}
