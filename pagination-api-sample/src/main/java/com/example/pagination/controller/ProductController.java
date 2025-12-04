package com.example.pagination.controller;

import com.example.pagination.dto.PageResponse;
import com.example.pagination.dto.ProductResponse;
import com.example.pagination.dto.ProductSearchRequest;
import com.example.pagination.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * GET /api/products?page=0&size=10&sortBy=createdAt&sortDirection=DESC
     * 전체 상품 목록 조회 (기본 페이지네이션)
     */
    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getAllProducts(
            @Valid @ModelAttribute ProductSearchRequest request
    ) {
        return ResponseEntity.ok(productService.getAllProducts(request));
    }

    /**
     * GET /api/products/category/{category}?page=0&size=10
     * 카테고리별 상품 조회
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<PageResponse<ProductResponse>> getProductsByCategory(
            @PathVariable String category,
            @Valid @ModelAttribute ProductSearchRequest request
    ) {
        return ResponseEntity.ok(productService.getProductsByCategory(category, request));
    }

    /**
     * GET /api/products/search?keyword=아이폰&page=0&size=10
     * 키워드 검색
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductResponse>> searchByKeyword(
            @RequestParam String keyword,
            @Valid @ModelAttribute ProductSearchRequest request
    ) {
        return ResponseEntity.ok(productService.searchByKeyword(keyword, request));
    }

    /**
     * GET /api/products/filter?category=전자제품&keyword=폰&page=0&size=10
     * 복합 조건 검색 (카테고리 + 키워드)
     */
    @GetMapping("/filter")
    public ResponseEntity<PageResponse<ProductResponse>> searchProducts(
            @Valid @ModelAttribute ProductSearchRequest request
    ) {
        return ResponseEntity.ok(productService.searchProducts(request));
    }

    /**
     * GET /api/products/price-range?minPrice=10000&maxPrice=50000&page=0&size=10
     * 가격 범위 검색
     */
    @GetMapping("/price-range")
    public ResponseEntity<PageResponse<ProductResponse>> getProductsByPriceRange(
            @Valid @ModelAttribute ProductSearchRequest request
    ) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(request));
    }
}
