package com.pharmanow.catalog.service.impl;

import com.pharmanow.catalog.dto.ProductDTO;
import com.pharmanow.catalog.entity.ProductEntity;
import com.pharmanow.catalog.exceptions.ProductNotFoundException;
import com.pharmanow.catalog.repository.ProductRepository;
import com.pharmanow.catalog.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        ProductEntity productEntity = toEntity(dto);
        ProductEntity savedEntity = productRepository.save(productEntity);
        return toDto(savedEntity);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ProductDTO getProductBySku(String sku) {
        ProductEntity bySku = productRepository.findBySku(sku);
        if (bySku == null) {
            throw new ProductNotFoundException("Product not found with sku: " + sku);
        }
        return toDto(bySku);
    }

    @Override
    public ProductDTO updateProductBySku(String sku, ProductDTO dto) {
        ProductEntity productEntity = productRepository.findBySku(sku);
        if (productEntity == null) {
            throw new ProductNotFoundException("Product not found with sku: " + sku);
        }
        // This is a full update (PUT). For partial updates (PATCH), you'd check for nulls.
        if (dto.getName() != null) productEntity.setName(dto.getName());
        if (dto.getBrand() != null) productEntity.setBrand(dto.getBrand());
        if (dto.getDescription() != null) productEntity.setDescription(dto.getDescription());
        if (dto.getPrice() != null) productEntity.setPrice(dto.getPrice());
        if (dto.getMg() != null) productEntity.setMg(dto.getMg());
        if (dto.getStockQuantity() != null) productEntity.setStockQuantity(dto.getStockQuantity());
        if (dto.getCategory() != null) productEntity.setCategory(dto.getCategory());
        if (dto.getImageUrl() != null) productEntity.setImageUrl(dto.getImageUrl());
        if (dto.getPrescriptionRequired() != null) productEntity.setPrescriptionRequired(dto.getPrescriptionRequired());

        ProductEntity updatedEntity = productRepository.save(productEntity);
        return toDto(updatedEntity);
    }

    @Override
    public void deleteProductBySku(String sku) {
        if (!productRepository.existsBySku(sku)) {
            throw new ProductNotFoundException("Product not found with sku: " + sku);
        }
        productRepository.deleteBySku(sku);
    }

    public Page<ProductDTO> searchProducts(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name())
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductEntity> productPage = (keyword == null || keyword.isBlank())
                ? productRepository.findAll(pageable)
                : productRepository.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(keyword, keyword, pageable);

        return productPage.map(this::toDto);
    }

    private ProductEntity toEntity(ProductDTO dto) {
        return ProductEntity.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .mg(dto.getMg())
                .stockQuantity(dto.getStockQuantity())
                .category(dto.getCategory())
                .imageUrl(dto.getImageUrl())
                .prescriptionRequired(dto.getPrescriptionRequired() != null && dto.getPrescriptionRequired())
                .build();
    }

    private ProductDTO toDto(ProductEntity entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .mg(entity.getMg())
                .stockQuantity(entity.getStockQuantity())
                .category(entity.getCategory())
                .imageUrl(entity.getImageUrl())
                .sku(entity.getSku())
                .prescriptionRequired(entity.isPrescriptionRequired())
                .build();
    }

}
