package com.allen.merchant.api;

import com.allen.merchant.dto.OrderDto;
import com.allen.merchant.service.MerchantService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/merchant")
public class MerchantApi {

    @Resource
    private MerchantService merchantService;

    @RequestMapping("/tradeOrder")
    public String tradeOrder(@RequestBody OrderDto orderDto){

        merchantService.tradeOrder(orderDto.getTransactionContext(), orderDto.getOrder());

        return "success";
    }
}
