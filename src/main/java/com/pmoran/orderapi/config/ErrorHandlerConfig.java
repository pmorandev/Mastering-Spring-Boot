package com.pmoran.orderapi.config;

import com.pmoran.orderapi.exceptions.GeneralServiceException;
import com.pmoran.orderapi.exceptions.NoDataFoundException;
import com.pmoran.orderapi.exceptions.ValidateServiceException;
import com.pmoran.orderapi.utils.WrapperResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> all(Exception e, WebRequest request){
        log.error(e.getMessage(), e);
        return new WrapperResponse(false, "Internal Server Error", null)
                .createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateServiceException.class)
    public ResponseEntity<?> validateServiceException(ValidateServiceException e, WebRequest request){
        return new WrapperResponse(false, e.getMessage(), null)
                .createResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> noDataFoundException(NoDataFoundException e, WebRequest request){
        return new WrapperResponse(false, e.getMessage(), null)
                .createResponse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GeneralServiceException.class)
    public ResponseEntity<?> generalServiceException(GeneralServiceException e, WebRequest request){
        log.error(e.getMessage(), e);
        return new WrapperResponse(false, "Internal Server Error", null)
                .createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
