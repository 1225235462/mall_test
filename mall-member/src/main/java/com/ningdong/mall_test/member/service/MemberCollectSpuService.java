package com.ningdong.mall_test.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ningdong.common.utils.PageUtils;
import com.ningdong.mall_test.member.entity.MemberCollectSpuEntity;

import java.util.Map;

/**
 * 会员收藏的商品
 *
 * @author ningdong
 * @email ningdong6175@qq.com
 * @date 2023-09-27 10:10:11
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

