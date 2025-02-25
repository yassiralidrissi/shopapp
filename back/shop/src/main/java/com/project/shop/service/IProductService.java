package com.project.shop.service;

import com.project.shop.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {

    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}
