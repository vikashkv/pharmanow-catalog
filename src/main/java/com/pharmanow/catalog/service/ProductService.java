package com.pharmanow.catalog.service;

import com.pharmanow.catalog.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO dto);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductBySku(String sku);

    ProductDTO updateProductBySku(String sku, ProductDTO dto);

    void deleteProductBySku(String sku);

    Page<ProductDTO> searchProducts(String search, int page, int size, String sortBy, String direction);
}