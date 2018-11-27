package com.allen.order.service;

import com.allen.order.domain.Order;
import org.mengyun.tcctransaction.api.TransactionContext;

public interface MerchantService {

    String tradeOrder(TransactionContext transactionContext, Order order);
}
