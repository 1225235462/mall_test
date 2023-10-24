package com.ningdong.mall_test.product.web;

import com.ningdong.mall_test.product.entity.CategoryEntity;
import com.ningdong.mall_test.product.service.CategoryService;
import com.ningdong.mall_test.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){

        List<CategoryEntity> categoryEntities = categoryService.getLevel1Category();

        model.addAttribute("categorys",categoryEntities);
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String,List<Catelog2Vo>> getCalalogJson(){

        Map<String,List<Catelog2Vo>> map = categoryService.getCatalogJson();

        return map;
    }
}
