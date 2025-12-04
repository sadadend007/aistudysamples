package com.example.pagination.service;

import com.example.pagination.dto.PageResponse;
import com.example.pagination.dto.ProductResponse;
import com.example.pagination.dto.ProductSearchRequest;
import com.example.pagination.entity.Product;
import com.example.pagination.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 유즈케이스 1: 전체 상품 목록 조회 (기본 페이지네이션)
     */
    public PageResponse<ProductResponse> getAllProducts(ProductSearchRequest request) {
        Page<Product> productPage = productRepository.findAll(request.toPageable());
        return PageResponse.from(productPage, ProductResponse::from);
    }

    /**
     * 유즈케이스 2: 카테고리별 상품 조회
     */
    public PageResponse<ProductResponse> getProductsByCategory(String category, ProductSearchRequest request) {
        Page<Product> productPage = productRepository.findByCategory(category, request.toPageable());
        return PageResponse.from(productPage, ProductResponse::from);
    }

    /**
     * 유즈케이스 3: 키워드 검색 (상품명)
     */
    public PageResponse<ProductResponse> searchByKeyword(String keyword, ProductSearchRequest request) {
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(keyword, request.toPageable());
        return PageResponse.from(productPage, ProductResponse::from);
    }

    /**
     * 유즈케이스 4: 복합 조건 검색 (카테고리 + 키워드)
     */
    public PageResponse<ProductResponse> searchProducts(ProductSearchRequest request) {
        Page<Product> productPage = productRepository.searchProducts(
                request.getCategory(),
                request.getKeyword(),
                request.toPageable()
        );
        return PageResponse.from(productPage, ProductResponse::from);
    }

    /**
     * 유즈케이스 5: 가격 범위 검색
     */
    public PageResponse<ProductResponse> getProductsByPriceRange(ProductSearchRequest request) {
        Page<Product> productPage = productRepository.findByPriceBetween(
                request.getMinPrice(),
                request.getMaxPrice(),
                request.toPageable()
        );
        return PageResponse.from(productPage, ProductResponse::from);
    }
}
