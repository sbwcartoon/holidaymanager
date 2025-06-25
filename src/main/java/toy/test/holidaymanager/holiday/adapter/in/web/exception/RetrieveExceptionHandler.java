package toy.test.holidaymanager.holiday.adapter.in.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toy.test.holidaymanager.holiday.adapter.out.persistence.exception.HolidayNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@RestControllerAdvice
public class RetrieveExceptionHandler {
    private final HttpServletRequest request;

    @ExceptionHandler(HolidayNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleHolidayNotFoundException(final Exception e) {
        return ApiErrorResponse.build(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                request
        );
    }
}
