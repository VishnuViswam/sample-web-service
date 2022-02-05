package com.sample.webservice.controller;

import com.sample.webservice.exceptions.ApiHeaderException;
import com.sample.webservice.exceptions.NoDataFoundException;
import com.sample.webservice.exceptions.UnknownException;
import com.sample.webservice.models.ApiErrorResponse;
import com.sample.webservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Calendar;
import java.util.NoSuchElementException;

/**
 * This class is common exception handling controller
 * all general type exceptions will be handled here.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2022-02-05
 */
@ControllerAdvice
public class CommonExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandlingController.class);

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(Constants.WRONG_HTTP_METHOD,
                Constants.WRONG_HTTP_METHOD_ERROR_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(Constants.MANDATORY_FIELDS_ARE_NOT_PRESENT_CODE,
                Constants.MANDATORY_FIELDS_ARE_NOT_PRESENT_ERROR_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(Constants.INCORRECT_DATA_CODE,
                Constants.INCORRECT_DATA_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException nullPointerException) {
        logger.error(" <-------------- NullPointer exception ---------------->  ", nullPointerException);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(Constants.UNKNOWN_ERROR_CODE,
                Constants.UNKNOWN_ERROR_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException validationException) {
        logger.error(" <-------------- Validation exception ---------------->  ", validationException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(Constants.INCORRECT_DATA_CODE,
                Constants.INCORRECT_DATA_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(Constants.MANDATORY_FIELDS_ARE_NOT_PRESENT_CODE,
                Constants.MANDATORY_FIELDS_ARE_NOT_PRESENT_ERROR_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(Constants.INCORRECT_DATA_CODE,
                Constants.INCORRECT_DATA_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorResponse(Constants.NO_DATA_FOUND_CODE, Constants.NO_DATA_FOUND_MESSAGE, Calendar.getInstance().getTimeInMillis())
        );
    }


    @ExceptionHandler(ApiHeaderException.class)
    public ResponseEntity<Object> handleApiHeaderException(ApiHeaderException apiHeaderException) {
        logger.error(" <-------------- ApiHeader exception happened ---------------->  ", apiHeaderException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(apiHeaderException.getErrorCode(),
                apiHeaderException.getErrorMessage(), Calendar.getInstance().getTimeInMillis()));
    }

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<Object> handleUnknownException(UnknownException unknownException) {
        logger.error(" <-------------- Unknown exception happened ---------------->  ", unknownException);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(Constants.UNKNOWN_ERROR_CODE,
                Constants.UNKNOWN_ERROR_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }


    /**
     * To handle all other unknown exceptions
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCommonException(Exception exception) {
        logger.error(" <-------------- Unknown exception happened ---------------->  ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(Constants.UNKNOWN_ERROR_CODE,
                Constants.UNKNOWN_ERROR_MESSAGE, Calendar.getInstance().getTimeInMillis()));
    }

}
