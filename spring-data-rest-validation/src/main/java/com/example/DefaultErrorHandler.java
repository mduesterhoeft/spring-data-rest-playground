package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * We extend the spring data rest exception handler because by default it is just applied to the spring data rest package.
 * We register it globally so we have consistent mapping of error code
 */
@ControllerAdvice
public class DefaultErrorHandler extends RepositoryRestExceptionHandler {

    @Autowired
    public DefaultErrorHandler(MessageSource messageSource) {
        super(messageSource);
    }


    public static class DefaultError {

        private String errorClass;
        private String message;

        public DefaultError(Throwable throwable) {
            setErrorClass(throwable.getClass().getName());
            setMessage(throwable.getMessage());
        }

        public String getErrorClass() {
            return errorClass;
        }

        public void setErrorClass(String errorClass) {
            this.errorClass = errorClass;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}

