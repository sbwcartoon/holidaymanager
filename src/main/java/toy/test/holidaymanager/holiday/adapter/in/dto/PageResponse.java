package toy.test.holidaymanager.holiday.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "조회된 페이지 정보")
public record PageResponse<T>(
        @Schema(description = "조회된 데이터", example = "[]")
        List<T> content,

        @Schema(description = "조회된 페이지 번호(1부터 시작)", example = "1")
        int page,

        @Schema(description = "페이지별 항목 수", example = "10")
        int size,

        @Schema(description = "전체 페이지 수", example = "10")
        int totalPages,

        @Schema(description = "전체 항목 수", example = "100")
        long totalElements,

        @Schema(description = "첫 페이지 여부", allowableValues = {"true", "false"}, example = "true")
        boolean isFirst,

        @Schema(description = "마지막 페이지 여부", allowableValues = {"true", "false"}, example = "true")
        boolean isLast
) {
    public static <T> PageResponse<T> from(final Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast()
        );
    }
}
