package com.allen.order.client;

import com.allen.order.dto.OrderDto;
import com.allen.order.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-service")
public interface ProductClient {

    @RequestMapping("/product/tradeOrder")
    String tradeOrder(OrderDto orderDto);

    @RequestMapping("/product/findById")
    Product findById(@RequestParam("productId") Long productId);
}
