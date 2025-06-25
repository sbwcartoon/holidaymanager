package toy.test.holidaymanager.holiday.adapter.in.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GeneralExceptionHandler {
    private final HttpServletRequest request;

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiErrorResponse> handleArgumentNotValidException(final Exception e) {
        return ApiErrorResponse.build(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                request
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParam(final MissingServletRequestParameterException e) {
        return ApiErrorResponse.build(
                HttpStatus.BAD_REQUEST,
                "Missing Parameter '" + e.getParameterName() + "'",
                request
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleServerException(final Exception e) {
        log.error("RuntimeException", e);

        return ApiErrorResponse.build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getClass().getSimpleName(),
                request
        );
    }
}
