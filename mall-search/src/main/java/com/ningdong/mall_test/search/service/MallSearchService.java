package com.ningdong.mall_test.search.service;

import com.ningdong.mall_test.search.vo.SearchParam;
import com.ningdong.mall_test.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
