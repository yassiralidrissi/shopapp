package com.project.shop.service;

import com.project.shop.dto.ProductDto;
import com.project.shop.entity.Product;
import com.project.shop.mapper.ProductMapper;
import com.project.shop.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Start creating product and save it to the database");

        // map productDto to product entity
        Product product = productMapper.toEntity(productDto);

        // save product
        Product productSaved = productRepository.save(product);

        log.info("  Product saved to the database with id {}", productSaved.getId());

        return productMapper.toDTO(productSaved);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Retrieving all products from database");
        return productMapper.toDTOList(productRepository.findAll());
    }

    @Override
    public ProductDto getProductById(Long id) {
        log.info("Start retrieving product with id {} from database", id);
        return productRepository.findById(id).map(productMapper::toDTO).orElseThrow(() -> new RuntimeException("Product ID not found"));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
       log.info("Updating book with id {}", id);
       Product updatedProducted = productMapper.toEntity(productDto);
       return productRepository.findById(id)
               .map(product -> {
                   product.setName(updatedProducted.getName());
                   product.setDescription(updatedProducted.getDescription());
                   product.setPrice(updatedProducted.getPrice());
                   product.setQuantity(updatedProducted.getQuantity());
                   product.setInternalReference(updatedProducted.getInventoryStatus());

                   return productMapper.toDTO(productRepository.save(product));
               }).orElseThrow(() -> new RuntimeException("Product ID not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Start deleting product with id {}", id);
        productRepository.deleteById(id);
        log.info("  Product with id {} deleted successfully", id);
    }

}
