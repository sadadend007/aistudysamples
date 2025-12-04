package com.example.pagination.repository;

import com.example.pagination.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA의 Page, Pageable을 활용한 페이지네이션 Repository
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. 기본 페이지네이션 - JpaRepository의 findAll(Pageable) 사용

    // 2. 카테고리별 검색 + 페이지네이션
    Page<Product> findByCategory(String category, Pageable pageable);

    // 3. 이름 검색 (LIKE) + 페이지네이션
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // 4. 복합 조건 검색 + 페이지네이션 (JPQL)
    @Query("SELECT p FROM Product p WHERE " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> searchProducts(
            @Param("category") String category,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    // 5. 가격 범위 검색 + 페이지네이션
    Page<Product> findByPriceBetween(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, Pageable pageable);
}
