package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.ProductListProjection;
import com.example.mappers.ProductMapper;
import com.example.service.ProductService;
import com.example.service.RecommendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/api")
public class RecommendController {

    @Autowired
    RecommendService rService;

    @Autowired
    ProductService pService;
    
    @GetMapping(value="/random")
    public Map<String, Object> getRandom(@RequestParam Long code) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = rService.checkRecommend(code);
        try{
            if(list != null){

            }else{
                Long ret = pService.randomProduct(code);
                ProductListProjection product = pService.selectProductOne(ret);
                map.put("list", product);
            }
        }catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
    
    
}
