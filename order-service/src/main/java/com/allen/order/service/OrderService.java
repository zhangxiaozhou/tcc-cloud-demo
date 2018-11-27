package com.allen.order.service;

import com.allen.order.dao.OrderExample;
import com.allen.order.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> find(OrderExample orderExample);

    void createOrder(Integer userId, List<Long> productIds, List<Integer> qualitys, List<Long> prices );

    void payment(Order order);

    void confirmPayment(Order order);

    void cancelPayment(Order order);
}
