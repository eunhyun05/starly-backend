package kr.starly.backend.common.exception;

import kr.starly.backend.account.exception.AccountNotFoundException;
import kr.starly.backend.account.exception.EmailAlreadyExistsException;
import kr.starly.backend.auth.exception.InvalidCredentialsException;
import kr.starly.backend.common.response.ApiResponse;
import kr.starly.backend.common.response.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(WebExchangeBindException e) {
        ErrorCode errorCode = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> switch (error.getField()) {
                    case "email" -> ErrorCode.INVALID_EMAIL;
                    case "password" -> ErrorCode.INVALID_PASSWORD;
                    default -> ErrorCode.BAD_REQUEST;
                })
                .orElse(ErrorCode.BAD_REQUEST);

        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExists() {
        ErrorCode errorCode = ErrorCode.EMAIL_ALREADY_EXISTS;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials() {
        ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountNotFound() {
        ErrorCode errorCode = ErrorCode.ACCOUNT_NOT_FOUND;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode));
    }
}