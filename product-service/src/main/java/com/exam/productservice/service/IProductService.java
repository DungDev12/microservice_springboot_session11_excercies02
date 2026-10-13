package com.exam.productservice.service;

import com.exam.productservice.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getProducts();
    Product getProductById(Long id);
}
