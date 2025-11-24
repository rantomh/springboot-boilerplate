package com.rantomah.boilerplate.core.exception.handler;

import com.rantomah.boilerplate.core.ApiErrorDto;
import com.rantomah.boilerplate.core.exception.AuthenticationException;
import com.rantomah.boilerplate.core.exception.GenericException;
import com.rantomah.boilerplate.core.exception.ResourceAlreadyExistException;
import com.rantomah.boilerplate.core.exception.ResourceNotFoundException;
import com.rantomah.boilerplate.core.exception.UserDisabledException;
import com.rantomah.boilerplate.core.exception.UserLockedException;
import com.rantomah.boilerplate.core.exception.ValidationException;
import com.rantomah.boilerplate.core.helper.I18nHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A centralized error handler that intercepts exceptions thrown by the application and sends
 * appropriate HTTP responses. This class provides mappings for various exception types to specific
 * HTTP status codes and error messages.
 *
 * <p>The error handler utilizes {@link ControllerAdvice} to globally manage exceptions and provides
 * methods to handle authentication, validation, and other custom business errors. All custom
 * exceptions are translated into standardized error responses, enabling consistent error handling
 * and easier debugging for clients of the application.
 *
 * <p>Methods in this class use exception handling annotations such as {@link ExceptionHandler} to
 * map specific exceptions to handler methods. Error responses are mostly returned as {@link
 * ResponseEntity} objects and include relevant information like HTTP status, error messages, and
 * request paths.
 *
 * <p>This class also implements {@link AuthenticationEntryPoint} to handle authentication entry
 * point exceptions, sending a 401 UNAUTHORIZED response when authentication fails.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler {

    /**
     * An instance of {@link I18nHelper} that provides internationalization (i18n) support for
     * retrieving localized messages throughout the application. This field is used for managing
     * translations and generating user-friendly error messages or other localized content based on
     * the user's locale.
     *
     * <p>The {@link I18nHelper} resolves messages using a {@link ResourceBundleMessageSource} and
     * supports optional arguments for dynamic message formatting. Locale-specific resolution is
     * handled either through the current locale context or an explicitly specified locale.
     */
    private final I18nHelper i18n;

    private static final String MESSAGE_KEY = "message";

    private ApiErrorDto buildError(GenericException ex, HttpServletRequest request) {
        return ApiErrorDto.builder()
                .path(request.getRequestURI())
                .httpStatus(ex.getHttpStatus())
                .status(ex.getStatus())
                .code(ex.getCode())
                .message(i18n.get(ex.getMessage()))
                .build();
    }

    /**
     * Handles AccessDeniedException by sending a forbidden HTTP response with a custom error
     * message.
     *
     * @param request the HttpServletRequest instance containing request details
     * @param response the HttpServletResponse instance for sending the HTTP response
     * @param accessDeniedException the AccessDeniedException instance indicating the access denial
     * @throws IOException if an input or output error occurs while sending the error response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {
        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "Authorization Failed : " + accessDeniedException.getMessage());
    }

    /**
     * Handles the MethodArgumentNotValidException thrown during validation failures and constructs
     * a custom response entity with error details.
     *
     * @param ex the MethodArgumentNotValidException that contains details of the validation errors
     * @param request the HttpServletRequest object representing the HTTP request in which the
     *     exception occurred
     * @return a ResponseEntity containing a map of error details like timestamp, status, error
     *     message, validation errors, and the request path with an HTTP 400 Bad Request status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handle(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put(MESSAGE_KEY, i18n.get("error.default.validation-failed"));
        body.put("path", request.getRequestURI());
        List<Map<String, String>> errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(
                                error -> {
                                    assert error.getDefaultMessage() != null;
                                    return Map.of(
                                            "field",
                                            error.getField(),
                                            MESSAGE_KEY,
                                            error.getDefaultMessage());
                                })
                        .toList();
        body.put("bindingResult", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Handles IllegalArgumentException by constructing and returning a response entity with
     * relevant error details and an HTTP 400 (Bad Request) status.
     *
     * @param ex the IllegalArgumentException that was thrown
     * @param request the HttpServletRequest containing details about the request
     * @return a ResponseEntity containing an error response body with details such as timestamp,
     *     status, error message, and request path
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handle(
            IllegalArgumentException ex, HttpServletRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put(MESSAGE_KEY, ex.getMessage());
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Handles AuthenticationException by constructing an unauthorized HTTP response with relevant
     * error details.
     *
     * @param ex the AuthenticationException that occurred
     * @param request the HttpServletRequest containing details about the request during which the
     *     exception occurred
     * @return a ResponseEntity encapsulating an ApiErrorDto with error details and an HTTP 401
     *     Unauthorized status
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorDto> handle(
            AuthenticationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildError(ex, request), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    /**
     * Handles the ValidationException by creating an ApiErrorDto containing the error details and
     * returns it wrapped in a ResponseEntity with an HTTP 400 Bad Request status.
     *
     * @param ex the ValidationException instance containing validation error details
     * @param request the HttpServletRequest instance containing information about the HTTP request
     * @return a ResponseEntity containing an ApiErrorDto with error details and an HTTP 400 Bad
     *     Request status
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorDto> handle(ValidationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildError(ex, request), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    /**
     * Handles HttpMessageNotReadableException and returns an HTTP 400 Bad Request response.
     *
     * @param ex the HttpMessageNotReadableException that was thrown
     * @return a ResponseEntity with an HTTP 400 Bad Request status and no body
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handle(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Handles the occurrence of a ResourceNotFoundException by constructing a relevant error
     * response and returning it in a ResponseEntity with an HTTP 404 (Not Found) status.
     *
     * @param ex the ResourceNotFoundException instance providing details about the resource that
     *     was not found
     * @param request the HttpServletRequest containing details about the HTTP request during which
     *     the exception occurred
     * @return a ResponseEntity encapsulating an ApiErrorDto with error details such as code,
     *     status, message, path, and timestamp, along with an HTTP 404 (Not Found) status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handle(
            ResourceNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildError(ex, request), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    /**
     * Handles the occurrence of a ResourceAlreadyExistException by constructing an error response
     * and returning it in a ResponseEntity with an HTTP 409 (Conflict) status.
     *
     * @param ex the ResourceAlreadyExistException instance providing details about the conflict
     * @param request the HttpServletRequest containing details about the HTTP request during which
     *     the exception occurred
     * @return a ResponseEntity encapsulating an ApiErrorDto with error details such as code,
     *     status, message, path, and timestamp, along with an HTTP 409 (Conflict) status
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ApiErrorDto> handle(
            ResourceAlreadyExistException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildError(ex, request), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    /**
     * Handles the UserLockedException by constructing a response entity with an error DTO and
     * returning it with an HTTP 403 Forbidden status.
     *
     * @param ex the UserLockedException that occurred
     * @param request the HttpServletRequest containing details about the request during which the
     *     exception occurred
     * @return a ResponseEntity containing an ApiErrorDto with error details such as code, status,
     *     message, path, and timestamp, along with an HTTP 403 Forbidden status
     */
    @ExceptionHandler(UserLockedException.class)
    public ResponseEntity<ApiErrorDto> handle(UserLockedException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildError(ex, request), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    /**
     * Handles the occurrence of a UserDisabledException by constructing an error response and
     * returning it in a ResponseEntity with an HTTP 403 (Forbidden) status.
     *
     * @param ex the UserDisabledException that occurred
     * @param request the HttpServletRequest containing details about the request during which the
     *     exception occurred
     * @return a ResponseEntity encapsulating an ApiErrorDto with error details such as code,
     *     status, message, path, and timestamp, along with an HTTP 403 (Forbidden) status
     */
    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<ApiErrorDto> handle(
            UserDisabledException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildError(ex, request), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    /**
     * Handles occurrences of GenericException, constructs a response entity encapsulating the
     * details of the error using an ApiErrorDto, and returns it with an HTTP 500 Internal Server
     * Error status.
     *
     * @param ex the GenericException instance that occurred
     * @param request the HttpServletRequest containing details about the HTTP request during which
     *     the exception occurred
     * @return a ResponseEntity containing an ApiErrorDto with error details, such as code, status,
     *     message, the path of the request, and the timestamp, along with an HTTP 500 Internal
     *     Server Error status
     */
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiErrorDto> handle(GenericException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                buildError(ex, request), HttpStatus.valueOf(ex.getHttpStatus()));
    }
}
