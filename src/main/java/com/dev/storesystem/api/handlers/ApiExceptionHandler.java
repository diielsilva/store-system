package com.dev.storesystem.api.handlers;

import com.dev.storesystem.common.dtos.error.ErrorDetailsDto;
import com.dev.storesystem.domain.exceptions.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorDetailsDto> handleBusinessException(BusinessException exception,
                                                                      HttpServletRequest request) {
        var errorDetails = getErrorDetails(400, exception.getMessage(), request.getServletPath());
        return getResult(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDetailsDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        var errorDetails = getErrorDetails(400,
                "Os campos obrigatórios estão faltando ou foram preenchidos incorretamente!", request.getServletPath());
        for (ObjectError error : exception.getAllErrors()) {
            errorDetails.getDetails().add(error.getDefaultMessage());
        }
        return getResult(errorDetails);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorDetailsDto> handleMissingServletRequestParamException(
            MissingServletRequestParameterException exception,
            HttpServletRequest request) {
        var errorDetails = getErrorDetails(400,
                "Os parâmetros obrigatórios da requisição não foram fornecidos!", request.getServletPath());
        return getResult(errorDetails);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorDetailsDto> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception,
            HttpServletRequest request) {
        var errorDetails = getErrorDetails(405, "Este método HTTP não é suportado!",
                request.getServletPath());
        return getResult(errorDetails);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorDetailsDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception,
                                                                                    HttpServletRequest request) {
        var errorDetails = getErrorDetails(400, "O corpo da requisição é inválido",
                request.getServletPath());
        return getResult(errorDetails);
    }

    private ErrorDetailsDto getErrorDetails(Integer status, String message, String path) {
        var errorDetails = new ErrorDetailsDto();
        errorDetails.setTimestamps(OffsetDateTime.now());
        errorDetails.setStatus(status);
        errorDetails.setMessage(message);
        errorDetails.setPath(path);
        return errorDetails;
    }

    private ResponseEntity<ErrorDetailsDto> getResult(ErrorDetailsDto errorDetails) {
        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(errorDetails.getStatus()));
    }
}
