package com.leandoer.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.ZonedDateTime;

@Getter
@Setter
class ErrorEntity extends RepresentationModel<ErrorEntity> {
    private int errorCode;
    private String errorText;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime timestamp;

    ErrorEntity(int errorCode, String errorText, String message, ZonedDateTime timestamp) {
        this.errorCode = errorCode;
        this.errorText = errorText;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ErrorEntityBuilder builder() {
        return new ErrorEntityBuilder();
    }

    public int getErrorCode() {
        return this.errorCode;
    }


    public String toString() {
        return "CustomExceptionHandler.ErrorEntity(errorCode=" + this.getErrorCode() + ", errorText=" + this.getErrorText() + ", meassage=" + this.getMessage() + ", timestamp=" + this.getTimestamp() + ")";
    }

    public static class ErrorEntityBuilder {
        private int errorCode;
        private String errorText;
        private String message;
        private ZonedDateTime timestamp;

        ErrorEntityBuilder() {
        }

        public ErrorEntity.ErrorEntityBuilder errorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorEntity.ErrorEntityBuilder errorText(String errorText) {
            this.errorText = errorText;
            return this;
        }

        public ErrorEntity.ErrorEntityBuilder meassage(String meassage) {
            this.message = meassage;
            return this;
        }

        public ErrorEntity.ErrorEntityBuilder timestamp(ZonedDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorEntity build() {
            return new ErrorEntity(errorCode, errorText, message, timestamp);
        }

        public String toString() {
            return "CustomExceptionHandler.ErrorEntity.ErrorEntityBuilder(errorCode=" + this.errorCode + ", errorText=" + this.errorText + ", meassage=" + this.message + ", timestamp=" + this.timestamp + ")";
        }
    }
}