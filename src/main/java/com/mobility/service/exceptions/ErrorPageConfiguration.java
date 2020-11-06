package com.mobility.service.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class ErrorPageConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorPageConfiguration.class);

    @Autowired
    private ErrorAttributes errorAttributes;

    public ErrorPageConfiguration() {}

    @ResponseBody
    @ExceptionHandler({MobilityException.class})
    public ResponseEntity handleMobilityExceptions(final HttpServletRequest request, final MobilityException e) {
        ResponseStatus responseStatus = ((ResponseStatus[])e.getClass().getAnnotationsByType(ResponseStatus.class))[0];
        if (e instanceof MobilitySystemException) {
            LOG.error("A system exception occurred.", e);
        } else {
            LOG.debug("An {} occurred: {}.", e.getClass().getSimpleName(), e.getMessage(), e);
        }
        Map<String, Object> error = this.errorAttributes.getErrorAttributes(new ServletWebRequest(request), false);
        error.put("message", e.getMessage());
        error.put("status", responseStatus.value().value());
        error.put("error", responseStatus.value().getReasonPhrase());
        error.remove("exception");
        return new ResponseEntity(error, responseStatus.value());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleConverterErrors(HttpServletRequest req, MethodArgumentTypeMismatchException exception) {
        Map<String, Object> error = this.errorAttributes.getErrorAttributes(new ServletWebRequest(req), false);
        error.put("message", exception.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Bad Request");
        error.remove("exception");
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
