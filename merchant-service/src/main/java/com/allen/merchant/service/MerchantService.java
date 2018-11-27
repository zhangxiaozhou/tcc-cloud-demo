package com.allen.merchant.service;

import com.allen.merchant.dto.Order;
import org.mengyun.tcctransaction.api.TransactionContext;

public interface MerchantService {

    void tradeOrder(TransactionContext transactionContext, Order order);

    void confirmTradeOrder(TransactionContext transactionContext, Order order);

    void cancelTradeOrder(TransactionContext transactionContext, Order order);
}
