package com.allen.merchant.dao;

import com.allen.merchant.domain.MerchantAccountHistory;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantAccountHistoryDao {
    long countByExample(MerchantAccountHistoryExample example);

    int deleteByExample(MerchantAccountHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MerchantAccountHistory record);

    int insertSelective(MerchantAccountHistory record);

    List<MerchantAccountHistory> selectByExample(MerchantAccountHistoryExample example);

    MerchantAccountHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MerchantAccountHistory record, @Param("example") MerchantAccountHistoryExample example);

    int updateByExample(@Param("record") MerchantAccountHistory record, @Param("example") MerchantAccountHistoryExample example);

    int updateByPrimaryKeySelective(MerchantAccountHistory record);

    int updateByPrimaryKey(MerchantAccountHistory record);
}