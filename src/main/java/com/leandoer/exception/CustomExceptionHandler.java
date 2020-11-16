package com.leandoer.exception;

import com.leandoer.controller.MainController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected  <T extends EntityException> ErrorEntity handleEntityExceptions(T ex, ServletWebRequest request) {
        return ErrorEntity.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorText(ex.getClass().getSimpleName())
                .meassage(ex.getMessage())
                .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build().add(
                        new Link(UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request.getRequest())).toUriString()).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(MainController.class).root()).withRel("root")

                );
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request ) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ErrorEntity.builder()
                .errorCode(status.value())
                .errorText(status.getReasonPhrase())
                .meassage(ex.getMessage())
                .timestamp(ZonedDateTime.now())
                .build().add(
                        new Link(((ServletWebRequest)request).getRequest().getRequestURI()).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(MainController.class).root()).withRel("root")
                ));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorEntity.builder()
                .errorCode(status.value())
                .errorText(status.getReasonPhrase())
                .meassage(ex.getMessage())
                .timestamp(ZonedDateTime.now())
                .build().add(
                        new Link(((ServletWebRequest)request).getRequest().getRequestURI()).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(MainController.class).root()).withRel("root")
                ));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorEntity.builder()
                .errorCode(status.value())
                .errorText(status.getReasonPhrase())
                .meassage(ex.getMessage())
                .timestamp(ZonedDateTime.now())
                .build().add(
                        new Link(((ServletWebRequest)request).getRequest().getRequestURI()).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(MainController.class).root()).withRel("root")
                ));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
