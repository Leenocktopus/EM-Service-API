package com.leandoer.exception;

import com.leandoer.controller.MainController;
import io.jsonwebtoken.JwtException;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JwtExceptionHandler {

    public ErrorEntity handleJwtException(JwtException ex, HttpServletRequest request) {
        ErrorEntity e = ErrorEntity.builder()
                .errorCode(901)
                .errorText(ex.getClass().getSimpleName())
                .meassage(ex.getMessage())
                .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();
        return e.add(
                new Link(request.getRequestURI()).withSelfRel(),
                linkTo(methodOn(MainController.class).root()).withRel("root")
        );
    }

}
