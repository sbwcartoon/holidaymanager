package toy.test.holidaymanager.holiday.adapter.in.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ApiErrorResponse {
    @Builder.Default
    private final String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public static ResponseEntity<ApiErrorResponse> build(
            final HttpStatus status,
            final String message,
            final HttpServletRequest request
    ) {
        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.builder()
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(message)
                        .path(request.getRequestURI())
                        .build());
    }
}