package com.pharmanow.catalog.controller;

import com.pharmanow.catalog.dto.ProductDTO;
import com.pharmanow.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all-products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/create-product")
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO));
    }

    @GetMapping("/get-product-by-sku/{sku}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String sku) {
        return ResponseEntity.ok(productService.getProductBySku(sku));
    }

    @PutMapping("/update-product-by-sku/{sku}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String sku, @Validated @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProductBySku(sku, productDTO));
    }

    @DeleteMapping("/delete-product-by-sku/{sku}")
    public ResponseEntity<String> deleteProduct(@PathVariable String sku) {
        productService.deleteProductBySku(sku);
        return ResponseEntity.ok("Product deleted successfully");
    }

    //@GetMapping("/all-products")
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(productService.searchProducts(search, page, size, sortBy, direction));
    }
}
