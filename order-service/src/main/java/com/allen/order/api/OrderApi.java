package com.allen.order.api;

import com.allen.order.dao.OrderDao;
import com.allen.order.dao.OrderItemDao;
import com.allen.order.dao.OrderItemExample;
import com.allen.order.domain.Order;
import com.allen.order.domain.OrderItem;
import com.allen.order.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderApi {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderItemDao orderItemDao;

    @RequestMapping("/payment/{orderId}")
    public String payment(@PathVariable("orderId") Long orderId){

        Order order = orderDao.selectByPrimaryKey(orderId);

        OrderItemExample orderItemExample = new OrderItemExample();
        OrderItemExample.Criteria criteria = orderItemExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemExample);

        order.setOrderItemList(orderItems);

        orderService.payment(order);

        return "success";
    }
}
