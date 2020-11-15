package com.leandoer.exception;

import com.leandoer.controller.MainController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityConflictException.class, EntityNotFoundException.class, IllegalEntityException.class})
    @ResponseStatus(HttpStatus.OK)
    public <T extends EntityException> ErrorEntity handleEntityExceptions(T ex, ServletWebRequest webRequest) {
        ErrorEntity e = ErrorEntity.builder()
                .errorCode(ex.getCode())
                .errorText(ex.getClass().getSimpleName())
                .meassage(ex.getMessage())
                .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();
        e.add(
                new Link(UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(webRequest.getRequest())).toUriString()).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(MainController.class).root()).withRel("root")

        );
        return e;
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

}
