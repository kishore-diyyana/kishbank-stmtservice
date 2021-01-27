package com.kishbank.stmtservice.validator;

import java.util.ArrayList;
import java.util.List;

import com.kishbank.stmtservice.util.StmtBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage()); //TODO Log exceptions when integrated with logging stream
        return new ResponseEntity(StmtBuilder.buildResponseBody(
                HttpStatus.INTERNAL_SERVER_ERROR.name()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}