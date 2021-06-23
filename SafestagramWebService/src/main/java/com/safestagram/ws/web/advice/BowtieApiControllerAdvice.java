package com.safestagram.ws.web.advice;


import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ResponseBody
@ControllerAdvice
public class BowtieApiControllerAdvice {
    @Value("${spring.application.name}")
    private String serviceName;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(Objects::nonNull)
                .filter(fieldError -> StringUtils.isNotEmpty(fieldError.getField()))
                .map(f -> f.getField().concat(" ").concat("cannot be empty!"))
                .collect(Collectors.joining());

        return ErrorResponse.builder().serviceName(serviceName).message(message).code(ErrorCode.ARGUMENT_NOT_VALID.getCode()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex, HttpServletRequest httpServletRequest) {
        log.error(httpServletRequest.getServletPath(), ex);
        ex.printStackTrace();
        return ErrorResponse.builder().serviceName(serviceName).message(ErrorCode.GENERAL_EXCEPTION.getMessage()).code(ErrorCode.ARGUMENT_NOT_VALID.getCode()).build();
    }

    @ExceptionHandler(SafestagramServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSafestagramServiceException(SafestagramServiceException ex) {
        return ErrorResponse.builder().message(ex.getMessage()).code(ex.getCode()).serviceName(serviceName).build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedException() {
        return ErrorResponse.builder().code(ErrorCode.UNAUTHORIZED.getCode()).message(ErrorCode.UNAUTHORIZED.getMessage()).serviceName(serviceName).build();
    }


}
