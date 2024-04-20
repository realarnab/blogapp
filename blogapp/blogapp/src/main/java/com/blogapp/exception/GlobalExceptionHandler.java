package com.blogapp.exception;

import com.blogapp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice //when any exception occur, it takes the exception object and give it to the handler method
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class) //specified the method that will handle specific exception
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFound ex, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),ex.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception e,WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),e.getMessage(),webRequest.getDescription(true));
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
