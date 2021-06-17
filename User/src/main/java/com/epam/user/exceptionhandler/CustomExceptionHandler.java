package com.epam.user.exceptionhandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.epam.user.exception.NoSuchUserException;
import com.epam.user.exception.ZeroUsersException;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		e.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		String errorMessage = errors.toString();
		logger.error(errorMessage);
		return new ResponseEntity<>(new ResponseTemplet(new Date(), errorMessage, request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = "Request body is missing or invalid";
		logger.error(errorMessage);
		return new ResponseEntity<>(new ResponseTemplet(new Date(), errorMessage, request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = "Method not supported. Please enter a valid HTTP method and URL";
		logger.error(errorMessage);
		return new ResponseEntity<>(new ResponseTemplet(new Date(), errorMessage, request.getDescription(false)),
				HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String errorMessage = "User ID should be a positive integer";
		logger.error(errorMessage);
		return new ResponseEntity<>(new ResponseTemplet(new Date(), errorMessage, request.getDescription(false)),
				HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(NoSuchUserException.class)
	public final ResponseEntity<ResponseTemplet> handleNoSuchUserException(NoSuchUserException e, WebRequest request) {
		String errorMessage = e.getMessage();
		logger.error(errorMessage);
		return new ResponseEntity<>(new ResponseTemplet(new Date(), errorMessage, request.getDescription(false)),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ZeroUsersException.class)
	public final ResponseEntity<ResponseTemplet> handleZeroUsersException(ZeroUsersException e, WebRequest request) {
		String errorMessage = e.getMessage();
		logger.error(errorMessage);
		return new ResponseEntity<>(new ResponseTemplet(new Date(), errorMessage, request.getDescription(false)),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ResponseTemplet> handleConstraintViolationException(ConstraintViolationException e,
			WebRequest request) {
		List<String> errors = new ArrayList<>();
		e.getConstraintViolations().forEach(error -> errors.add(error.getMessage()));
		String errorMessage = errors.toString();
		logger.error(errorMessage);
		return new ResponseEntity<>(new ResponseTemplet(new Date(), errorMessage, request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

}