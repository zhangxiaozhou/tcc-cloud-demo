package com.allen.order.service.impl;

import com.allen.order.client.ProductClient;
import com.allen.order.dao.OrderDao;
import com.allen.order.dao.OrderExample;
import com.allen.order.domain.Order;
import com.allen.order.dto.Product;
import com.allen.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceImplTest {

    @Resource
    private OrderService orderService;

    @Resource
    private ProductClient productClient;

    @Test
    public void createOrder() {

        Random ramdom = new Random();

        for(int i=0; i<10000; i++){
            Integer userId = 1001;

            List<Long> productIds = new ArrayList<>();
            List<Integer> qualitys = new ArrayList<>();
            List<Long> prices = new ArrayList<>();

            for(int j=0; j<3; j++){
                int nextInt = ramdom.nextInt(10);

                Long productId = Long.valueOf(nextInt%9 + 1);
                productIds.add(productId);

                int quality = nextInt%2+1;
                qualitys.add(quality);

                Product product = productClient.findById(productId);
                prices.add(product.getPrice());
            }

            orderService.createOrder(userId, productIds, qualitys, prices);
        }
    }

    @Test
    public void find() {

        OrderExample orderExample = new OrderExample();
        orderExample.setLimit(10);
        orderExample.setOffset(0l);

        List<Order> orders = orderService.find(orderExample);

        System.out.println(orders);
    }

    @Test
    public void payment() {

        for(int i=1; i<1000; i++){
            try{
                OrderExample orderExample = new OrderExample();
                OrderExample.Criteria criteria = orderExample.createCriteria();
                criteria.andStatusIn(Arrays.asList(0, 3));

                orderExample.setOffset(Long.valueOf(i*10));
                orderExample.setLimit(50);

                List<Order> orders = orderService.find(orderExample);

                if(orders!=null && !orders.isEmpty()){
                    for(Order order : orders){
                        orderService.payment(order);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}