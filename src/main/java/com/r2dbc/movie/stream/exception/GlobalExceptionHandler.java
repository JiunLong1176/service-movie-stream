package com.r2dbc.movie.stream.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.r2dbc.movie.stream.controller.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "com.r2dbc.movie.stream.controller")
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    ResponseEntity handleBusinessException(BusinessException ex) {
        log.debug("handling business exception::" + ex);
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("context", ex.getContext());
        node.put("code", ex.getCode());
        node.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(node);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity handleRuntimeException(Exception ex) {
        log.debug("handling exception::" + ex);
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("context", "movie_steam");
        node.put("code", ResponseCode.INTERNAL_SERVER_ERROR.code);
        node.put("message", ex.getMessage());
        return ResponseEntity.internalServerError().body(node);
    }
}
