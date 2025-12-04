package com.example.pagination.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

/**
 * 페이지네이션 + 검색 조건을 담는 요청 DTO
 */
@Getter
@Setter
public class ProductSearchRequest {

    // 페이지네이션 파라미터
    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다")
    private int page = 0;

    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다")
    @Max(value = 100, message = "페이지 크기는 100 이하여야 합니다")
    private int size = 10;

    private String sortBy = "createdAt";
    private String sortDirection = "DESC";

    // 검색 조건
    private String keyword;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    /**
     * Pageable 객체 생성
     */
    public Pageable toPageable() {
        Sort sort = Sort.by(
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        return PageRequest.of(page, size, sort);
    }

    /**
     * 다중 정렬 지원 Pageable 생성
     */
    public Pageable toPageable(String... properties) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), properties);
        return PageRequest.of(page, size, sort);
    }
}
