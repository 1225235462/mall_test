package com.ningdong.mall_test.product.feign;

import com.ningdong.common.to.SkuReductionTo;
import com.ningdong.common.to.SpuBoundTo;
import com.ningdong.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuRedution(@RequestBody SkuReductionTo skuReductionTo);
}
