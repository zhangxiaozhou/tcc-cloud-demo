package com.allen.order.service.impl;

import com.allen.order.client.MerchantClient;
import com.allen.order.domain.Order;
import com.allen.order.dto.OrderDto;
import com.allen.order.service.MerchantService;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.Propagation;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class MerchantServiceProxy implements MerchantService {

    @Resource
    private MerchantClient merchantClient;

    @Compensable(propagation = Propagation.SUPPORTS, confirmMethod = "tradeOrder", cancelMethod = "tradeOrder", transactionContextEditor = Compensable.DefaultTransactionContextEditor.class)
    public String tradeOrder(TransactionContext transactionContext, Order order) {

        OrderDto orderDto = new OrderDto();
        orderDto.setOrder(order);
        orderDto.setTransactionContext(transactionContext);

        return merchantClient.tradeOrder(orderDto);
    }
}
