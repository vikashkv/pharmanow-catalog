package com.pharmanow.catalog.repository;

import com.pharmanow.catalog.entity.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsBySku(String sku);

    void deleteBySku(String sku);

    ProductEntity findBySku(String sku);

    Page<ProductEntity> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(
            String name, String sku, Pageable pageable);
}
