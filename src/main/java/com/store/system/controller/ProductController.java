package com.store.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.store.system.data.BaseResponse;
import com.store.system.entity.Product;
import com.store.system.service.ProductService;
import com.store.system.utils.JsonRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("insert")
    public BaseResponse insertProduct(HttpServletRequest request) {
        Product product = new Product();
        try {
            String requestString = JsonRequest.getPayload(request);
            product = JSONObject.parseObject(requestString, Product.class);

            productService.insertProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.responseError(e.getMessage());
        }
        return BaseResponse.responseSuccess(null, "添加商品成功");
    }

    @GetMapping("list")
    public BaseResponse getList() {
        return BaseResponse.responseSuccess(productService.findList(), "查询商品成功");
    }

    @PostMapping("remove")
    public BaseResponse removeProduct(HttpServletRequest request) {
        Product product = new Product();
        try {
            String requestString = JsonRequest.getPayload(request);
            product = JSONObject.parseObject(requestString, Product.class);
            if (product.getId() != 0) {
                productService.removeProduct(product.getId());
                return BaseResponse.responseSuccess(null, "删除商品成功");
            } else {
                return BaseResponse.responseError("id 为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.responseError(e.getMessage());
        }
    }

    @PostMapping("update")
    public BaseResponse updateList(HttpServletRequest request) {
        Product product = JSONObject.parseObject(JsonRequest.getPayload(request), Product.class);
        if (product.getId() != 0) {
            productService.updateList(product);
            return BaseResponse.responseSuccess(null, "更新商品成功");
        } else {
            return BaseResponse.responseError("id 为空");
        }


    }
}
