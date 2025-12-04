package com.example.pagination.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/**
 * 공통 페이지네이션 응답 DTO
 * Spring Data의 Page 객체를 API 응답에 적합한 형태로 변환
 */
@Getter
@Builder
public class PageResponse<T> {

    private List<T> content;           // 실제 데이터 목록
    private PageInfo pageInfo;         // 페이지 정보

    @Getter
    @Builder
    public static class PageInfo {
        private int page;              // 현재 페이지 번호 (0부터 시작)
        private int size;              // 페이지 크기
        private long totalElements;    // 전체 데이터 수
        private int totalPages;        // 전체 페이지 수
        private boolean first;         // 첫 페이지 여부
        private boolean last;          // 마지막 페이지 여부
        private boolean hasNext;       // 다음 페이지 존재 여부
        private boolean hasPrevious;   // 이전 페이지 존재 여부
    }

    /**
     * Page<Entity> -> PageResponse<DTO> 변환
     *
     * @param page   Spring Data Page 객체
     * @param mapper Entity를 DTO로 변환하는 함수
     */
    public static <E, D> PageResponse<D> from(Page<E> page, Function<E, D> mapper) {
        List<D> content = page.getContent().stream()
                .map(mapper)
                .toList();

        PageInfo pageInfo = PageInfo.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();

        return PageResponse.<D>builder()
                .content(content)
                .pageInfo(pageInfo)
                .build();
    }

    /**
     * Page<DTO>를 직접 변환 (이미 DTO인 경우)
     */
    public static <D> PageResponse<D> from(Page<D> page) {
        return from(page, Function.identity());
    }
}
