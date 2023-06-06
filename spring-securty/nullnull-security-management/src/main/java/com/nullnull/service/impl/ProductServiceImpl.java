package com.nullnull.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nullnull.domain.Product;
import com.nullnull.mapper.ProductMapper;
import com.nullnull.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public List<Product> findAll() {
        return null;
    }
}
