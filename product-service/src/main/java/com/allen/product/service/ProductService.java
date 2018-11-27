package com.allen.product.service;

import com.allen.product.dto.Order;
import org.mengyun.tcctransaction.api.TransactionContext;

public interface ProductService {

    void tradeOrder(TransactionContext transactionContext, Order order);
}
