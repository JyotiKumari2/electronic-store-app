package com.lcwd.electronic_store.exceptions;

import com.lcwd.electronic_store.dtos.ApiResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //Handler resource not found exception.

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        logger.info("Exception handler invoked");
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(ex.getMessage()).success(true).httpStatus(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.NOT_FOUND);
    }

    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
          List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response =new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);
        });
return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // handle bad api exception
    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex) {
        logger.info("Bad API request!!");
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(ex.getMessage()).success(false).httpStatus(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.BAD_REQUEST);
    }


}
