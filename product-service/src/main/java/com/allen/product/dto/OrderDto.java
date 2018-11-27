package com.allen.product.dto;

import org.mengyun.tcctransaction.api.TransactionContext;

public class OrderDto {

    private Order order;

    private TransactionContext transactionContext;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public TransactionContext getTransactionContext() {
        return transactionContext;
    }

    public void setTransactionContext(TransactionContext transactionContext) {
        this.transactionContext = transactionContext;
    }
}
