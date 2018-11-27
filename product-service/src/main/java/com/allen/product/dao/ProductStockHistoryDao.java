package com.allen.product.dao;

import com.allen.product.domain.ProductStockHistory;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductStockHistoryDao {
    long countByExample(ProductStockHistoryExample example);

    int deleteByExample(ProductStockHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductStockHistory record);

    int insertSelective(ProductStockHistory record);

    List<ProductStockHistory> selectByExample(ProductStockHistoryExample example);

    ProductStockHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductStockHistory record, @Param("example") ProductStockHistoryExample example);

    int updateByExample(@Param("record") ProductStockHistory record, @Param("example") ProductStockHistoryExample example);

    int updateByPrimaryKeySelective(ProductStockHistory record);

    int updateByPrimaryKey(ProductStockHistory record);
}