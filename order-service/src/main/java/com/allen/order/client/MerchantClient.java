package com.allen.order.client;

import com.allen.order.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "merchant-service")
public interface MerchantClient {

    @RequestMapping("/merchant/tradeOrder")
    String tradeOrder(OrderDto orderDto);
}
