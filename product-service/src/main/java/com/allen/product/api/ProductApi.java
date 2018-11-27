package com.allen.product.api;

import com.allen.product.dao.ProductDao;
import com.allen.product.domain.Product;
import com.allen.product.dto.OrderDto;
import com.allen.product.service.ProductService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductApi {

    @Resource
    private ProductDao productDao;

    @Resource
    private ProductService productService;

    @RequestMapping("/findById")
    public Product findById(Long productId){

        return productDao.selectByPrimaryKey(productId);
    }

    @RequestMapping("/tradeOrder")
    public String tradeOrder(@RequestBody OrderDto orderDto){

        productService.tradeOrder(orderDto.getTransactionContext(), orderDto.getOrder());

        return "success";
    }
}
