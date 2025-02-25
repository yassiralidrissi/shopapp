package com.project.shop.mapper;

import com.project.shop.dto.ProductDto;
import com.project.shop.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Entity to DTO
    ProductDto toDTO(Product product);

    // DTO to Entity
    Product toEntity(ProductDto productDto);

    // List Entity to List DTOs
    List<ProductDto> toDTOList(List<Product> books);

    // List DTOs to List Entity
    List<Product> toEntityList(List<ProductDto> productDtos);

}
