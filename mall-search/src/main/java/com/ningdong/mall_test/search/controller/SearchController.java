package com.ningdong.mall_test.search.controller;

import com.ningdong.mall_test.search.service.MallSearchService;
import com.ningdong.mall_test.search.vo.SearchParam;
import com.ningdong.mall_test.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @Autowired
    MallSearchService mallSearchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model){
        SearchResult res = mallSearchService.search(param);
        model.addAttribute("result",res);

        return "list";
    }
}
