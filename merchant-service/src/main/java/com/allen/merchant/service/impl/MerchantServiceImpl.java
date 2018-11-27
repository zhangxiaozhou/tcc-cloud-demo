package com.allen.merchant.service.impl;

import com.allen.merchant.dao.MerchantAccountHistoryDao;
import com.allen.merchant.dao.MerchantAccountHistoryExample;
import com.allen.merchant.domain.MerchantAccountHistory;
import com.allen.merchant.dto.Order;
import com.allen.merchant.dto.OrderItem;
import com.allen.merchant.service.MerchantService;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    private MerchantAccountHistoryDao merchantAccountHistoryDao;

    @Compensable(confirmMethod = "confirmTradeOrder", cancelMethod = "cancelTradeOrder", transactionContextEditor = Compensable.DefaultTransactionContextEditor.class)
    @Transactional
    public void tradeOrder(TransactionContext transactionContext, Order order){

        MerchantAccountHistoryExample merchantAccountHistoryExample = new MerchantAccountHistoryExample();
        MerchantAccountHistoryExample.Criteria criteria = merchantAccountHistoryExample.createCriteria();
        criteria.andOrderIdEqualTo(order.getId());
        List<MerchantAccountHistory> selectMerchantAccountHistories = merchantAccountHistoryDao.selectByExample(merchantAccountHistoryExample);

        //如果这个订单已经处理过，删除之前记录
        if(selectMerchantAccountHistories!=null && !selectMerchantAccountHistories.isEmpty()){
            for(MerchantAccountHistory merchantAccountHistory : selectMerchantAccountHistories){
                merchantAccountHistoryDao.deleteByPrimaryKey(merchantAccountHistory.getId());
            }
        }

        List<OrderItem> orderItemList = order.getOrderItemList();

        Set<Long> merchantIds = new HashSet<>();

        Set<MerchantAccountHistory> merchantAccountHistories = new LinkedHashSet<>();

        Map<Long, Long> merchantAmountMap = new HashMap<>();

        //循环订单项，确定每个商户需要加多少钱
        for(OrderItem orderItem : orderItemList){

            Long merchantId = orderItem.getMerchantId();

            merchantIds.add(merchantId);

            if(!merchantAmountMap.keySet().contains(merchantId)){
                merchantAmountMap.put(merchantId, 0l);
            }

            Long amount = merchantAmountMap.get(merchantId);
            amount += orderItem.getPrice()*orderItem.getQuality();
            merchantAmountMap.put(merchantId, amount);
        }

        //对每个订单，算好每个商户需要增加资金后，循环对每个商户账户历史表新增
        for(Long merchantId : merchantIds){

            MerchantAccountHistory merchantAccountHistory = new MerchantAccountHistory();
            merchantAccountHistory.setMerchantId(merchantId);
            merchantAccountHistory.setStates(0);
            merchantAccountHistory.setAmoutAction(1);
            merchantAccountHistory.setOrderId(order.getId());
            merchantAccountHistory.setAmount(merchantAmountMap.get(merchantId));
            merchantAccountHistories.add(merchantAccountHistory);
        }

        for(MerchantAccountHistory merchantAccountHistory : merchantAccountHistories){
            merchantAccountHistoryDao.insert(merchantAccountHistory);
        }

    }

    @Transactional
    public void confirmTradeOrder(TransactionContext transactionContext, Order order){

        MerchantAccountHistoryExample merchantAccountHistoryExample = new MerchantAccountHistoryExample();
        MerchantAccountHistoryExample.Criteria criteria = merchantAccountHistoryExample.createCriteria();
        criteria.andOrderIdEqualTo(order.getId());
        List<MerchantAccountHistory> selectMerchantAccountHistories = merchantAccountHistoryDao.selectByExample(merchantAccountHistoryExample);

        for(MerchantAccountHistory merchantAccountHistory : selectMerchantAccountHistories){
            merchantAccountHistory.setStates(1);
            merchantAccountHistoryDao.updateByPrimaryKey(merchantAccountHistory);
        }
    }

    @Transactional
    public void cancelTradeOrder(TransactionContext transactionContext, Order order){

        MerchantAccountHistoryExample merchantAccountHistoryExample = new MerchantAccountHistoryExample();
        MerchantAccountHistoryExample.Criteria criteria = merchantAccountHistoryExample.createCriteria();
        criteria.andOrderIdEqualTo(order.getId());
        List<MerchantAccountHistory> selectMerchantAccountHistories = merchantAccountHistoryDao.selectByExample(merchantAccountHistoryExample);

        for(MerchantAccountHistory merchantAccountHistory : selectMerchantAccountHistories){

            merchantAccountHistoryDao.deleteByPrimaryKey(merchantAccountHistory.getId());
        }
    }
}
