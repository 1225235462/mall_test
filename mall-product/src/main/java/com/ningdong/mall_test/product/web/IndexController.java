package com.ningdong.mall_test.product.web;

import com.ningdong.mall_test.product.entity.CategoryEntity;
import com.ningdong.mall_test.product.service.CategoryService;
import com.ningdong.mall_test.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Resource
    private RedissonClient redissonClient;

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

    @ResponseBody
    @GetMapping("/hello")
    public String test(){
        RLock lock1 = redissonClient.getLock("lock1");

        lock1.lock();
        try {
            System.out.println("lock successd!====="+Thread.currentThread().getId());
            Thread.sleep(10000);
        }catch (Exception ignored){

        }finally {
            System.out.println("unlock!===="+Thread.currentThread().getId());
            lock1.unlock();
        }

        return "Hello";
    }
}
