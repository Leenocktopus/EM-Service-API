package com.leandoer.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leandoer.exception.EntityConflictException;
import com.leandoer.exception.EntityException;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.exception.IllegalEntityException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityConflictException.class, EntityNotFoundException.class, IllegalEntityException.class})
    public <T extends EntityException> ResponseEntity<ErrorEntity> handleEntityExceptions(T ex, ServletWebRequest webRequest) {
        ErrorEntity e = ErrorEntity.builder()
                .errorCode(ex.getCode())
                .errorText(ex.getClass().getSimpleName())
                .meassage(ex.getMessage())
                .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();
        e.add(
                new Link(UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(webRequest.getRequest())).toUriString()).withSelfRel(),
                linkTo(methodOn(MainController.class).root()).withRel("root")

        );
        System.out.println(e);
        return new ResponseEntity<>(
                e,
                HttpStatus.OK
        );
    }


    @Builder
    @ToString
    @Getter
    @Setter
    static class ErrorEntity extends RepresentationModel<ErrorEntity> {
        private int errorCode;
        private String errorText;
        private String meassage;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime timestamp;
    }
}
