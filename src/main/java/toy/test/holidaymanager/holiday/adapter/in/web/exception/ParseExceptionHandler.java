package toy.test.holidaymanager.holiday.adapter.in.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@RestControllerAdvice
public class ParseExceptionHandler {
    private final HttpServletRequest request;

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ApiErrorResponse> handleParseException(final Exception e) {
        return ApiErrorResponse.build(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                request
        );
    }
}
