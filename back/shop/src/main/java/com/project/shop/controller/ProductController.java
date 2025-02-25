package com.project.shop.controller;

import com.project.shop.dto.ProductDto;
import com.project.shop.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("@customSecurityChecker.isAdminEmail()")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto productCreated = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> retrieveAllProducts() {
        List<ProductDto> listProducts = productService.getAllProducts();
        return ResponseEntity.ok().body(listProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> retrieveProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@customSecurityChecker.isAdminEmail()")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id, ProductDto productDto) {
        ProductDto productUpdated = productService.updateProduct(id, productDto);
        return ResponseEntity.ok().body(productUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityChecker.isAdminEmail()")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
