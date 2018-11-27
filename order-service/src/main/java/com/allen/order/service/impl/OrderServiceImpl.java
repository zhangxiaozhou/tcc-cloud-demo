package com.allen.order.service.impl;

import com.allen.order.client.ProductClient;
import com.allen.order.dao.OrderDao;
import com.allen.order.dao.OrderExample;
import com.allen.order.dao.OrderItemDao;
import com.allen.order.domain.Order;
import com.allen.order.domain.OrderItem;
import com.allen.order.dto.Product;
import com.allen.order.service.MerchantService;
import com.allen.order.service.OrderService;
import com.allen.order.service.ProductService;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private MerchantService merchantService;

    @Resource
    private ProductService productService;

    @Resource
    private ProductClient productClient;


    public List<Order> find(OrderExample orderExample){

        List<Order> orders = orderDao.selectWithItemByExample(orderExample);

        return orders;
    }

    public void createOrder(Integer userId, List<Long> productIds, List<Integer> qualitys, List<Long> prices ){

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setStatus(0);
        order.setUserId(userId);
        int insert = orderDao.insert(order);

        for(int i=0; i<productIds.size(); i++){

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());

            Long productId = productIds.get(i);
            orderItem.setProductId(productId);

            Product product = productClient.findById(productId);

            orderItem.setMerchantId(product.getMerchantId());

            orderItem.setQuality(qualitys.get(i));
            orderItem.setPrice(prices.get(i));
            orderItemDao.insert(orderItem);
        }
    }


    @Compensable(confirmMethod = "confirmPayment", cancelMethod = "cancelPayment", asyncConfirm = true)
    @Transactional
    public void payment(Order order){

        if(order.getStatus().equals(0) || order.getStatus().equals(3)){
            order.setStatus(1);
            orderDao.updateByPrimaryKey(order);

            String merchantTradeOrderResult = merchantService.tradeOrder(null, order);
            String productTradeOrderResult = productService.tradeOrder(null, order);
        }

    }

    @Transactional
    public void confirmPayment(Order order){

        if(order.getStatus().equals(1)){
            order.setStatus(2);
            orderDao.updateByPrimaryKey(order);
        }
    }

    @Transactional
    public void cancelPayment(Order order){

        order.setStatus(3);
        orderDao.updateByPrimaryKey(order);
    }
}
