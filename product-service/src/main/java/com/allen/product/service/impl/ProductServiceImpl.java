package com.allen.product.service.impl;

import com.allen.product.dao.ProductDao;
import com.allen.product.dao.ProductStockHistoryDao;
import com.allen.product.dao.ProductStockHistoryExample;
import com.allen.product.domain.Product;
import com.allen.product.domain.ProductStockHistory;
import com.allen.product.dto.Order;
import com.allen.product.dto.OrderItem;
import com.allen.product.service.ProductService;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductStockHistoryDao productStockHistoryDao;

    @Resource
    private ProductDao productDao;

    @Compensable(confirmMethod = "confirmTradeOrder", cancelMethod = "cancelTradeOrder", transactionContextEditor = Compensable.DefaultTransactionContextEditor.class)
    @Transactional
    public void tradeOrder(TransactionContext transactionContext, Order order) {

        ProductStockHistoryExample productStockHistoryExample = new ProductStockHistoryExample();
        ProductStockHistoryExample.Criteria criteria = productStockHistoryExample.createCriteria();
        criteria.andOrderIdEqualTo(order.getId());
        List<ProductStockHistory> selectProductStockHistories = productStockHistoryDao.selectByExample(productStockHistoryExample);

        if(selectProductStockHistories!=null && !selectProductStockHistories.isEmpty()){
            productStockHistoryDao.deleteByExample(productStockHistoryExample);
        }

        Map<Long, Integer> productQualityMap = new HashMap<>();

        for(OrderItem orderItem : order.getOrderItemList()){

            Long productId = orderItem.getProductId();

            if(!productQualityMap.keySet().contains(productId)){
                productQualityMap.put(productId, 0);
            }

            Integer quality = productQualityMap.get(productId);
            quality += orderItem.getQuality();
            productQualityMap.put(productId, quality);
        }

        for(Long productId : productQualityMap.keySet()){

            ProductStockHistory productStockHistory = new ProductStockHistory();
            productStockHistory.setOrderId(order.getId());
            productStockHistory.setProductId(productId);

            Product product = productDao.selectByPrimaryKey(productId);
            Integer stock = product.getStock();

            if(stock < productQualityMap.get(productId)){
                throw new RuntimeException("产品" + productId + "库存不足！");
            }

            productStockHistory.setQuality(productQualityMap.get(productId));
            productStockHistory.setStatus(0);
            productStockHistory.setStockAction(-1);

            productStockHistoryDao.insert(productStockHistory);
        }
    }


    @Transactional
    public void confirmTradeOrder(TransactionContext transactionContext, Order order) {

        ProductStockHistoryExample productStockHistoryExample = new ProductStockHistoryExample();
        ProductStockHistoryExample.Criteria criteria = productStockHistoryExample.createCriteria();
        criteria.andOrderIdEqualTo(order.getId());
        List<ProductStockHistory> selectProductStockHistories = productStockHistoryDao.selectByExample(productStockHistoryExample);

        if(selectProductStockHistories!=null && !selectProductStockHistories.isEmpty()){
            for(ProductStockHistory productStockHistory : selectProductStockHistories){
                productStockHistory.setStatus(1);
                productStockHistoryDao.updateByPrimaryKey(productStockHistory);
            }
        }
    }

    @Transactional
    public void cancelTradeOrder(TransactionContext transactionContext, Order order) {

        ProductStockHistoryExample productStockHistoryExample = new ProductStockHistoryExample();
        ProductStockHistoryExample.Criteria criteria = productStockHistoryExample.createCriteria();
        criteria.andOrderIdEqualTo(order.getId());
        List<ProductStockHistory> selectProductStockHistories = productStockHistoryDao.selectByExample(productStockHistoryExample);

        if(selectProductStockHistories!=null && !selectProductStockHistories.isEmpty()){
            productStockHistoryDao.deleteByExample(productStockHistoryExample);
        }
    }
}
