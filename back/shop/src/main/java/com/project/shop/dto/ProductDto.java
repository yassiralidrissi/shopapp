package com.project.shop.dto;

import java.time.LocalDateTime;

public record ProductDto
        (
                Long id,
                String code,
                String name,
                String description,
                String image,
                String category,
                double price,
                int quantity,
                String internalReference,
                int shellId,
                String inventoryStatus,
                double rating,
                LocalDateTime createdAt,
                LocalDateTime updateAt
        ) {

}


