package com.pharmanow.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private Double price;
    private Integer mg;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private String sku;
    private Boolean prescriptionRequired;
}