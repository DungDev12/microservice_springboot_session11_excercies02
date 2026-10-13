package com.exam.productservice.service;

import com.exam.productservice.entity.Product;
import com.exam.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final RedissonClient redissonClient;


    @Override
    @Cacheable(value = "products",key = "'all'")
    public List<Product> getProducts() {
        System.out.println("getProducts All");
        return productRepository.findAll();
    }

    @Override
    @Cacheable(value = "products",key = "#id")
    public Product getProductById(Long id) {
        System.out.println("getProducts By Id");
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Không tìm thấy sản phẩm"));
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public Product updateProductById(Long id, Product request) {
        Product product =  productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy sản phẩm"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());

        return productRepository.save(product);
    }


}
