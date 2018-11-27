package com.allen.order.service.impl;

import com.allen.order.client.ProductClient;
import com.allen.order.domain.Order;
import com.allen.order.dto.OrderDto;
import com.allen.order.service.ProductService;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.Propagation;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class ProductServiceProxy implements ProductService {

    @Resource
    private ProductClient productClient;

    @Compensable(propagation = Propagation.SUPPORTS, confirmMethod = "tradeOrder", cancelMethod = "tradeOrder", transactionContextEditor = Compensable.DefaultTransactionContextEditor.class)
    public String tradeOrder(TransactionContext transactionContext, Order order) {

        OrderDto orderDto = new OrderDto();
        orderDto.setTransactionContext(transactionContext);
        orderDto.setOrder(order);
        return productClient.tradeOrder(orderDto);
    }
}
