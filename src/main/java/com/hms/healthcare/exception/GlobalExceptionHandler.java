package com.hms.healthcare.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

	@RestControllerAdvice
	public class GlobalExceptionHandler {
	
		@ExceptionHandler(MethodArgumentNotValidException.class)
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		public Map<String, Object> handle(MethodArgumentNotValidException ex) {
			Map<String, String> errors = new HashMap<>();
			ex.getBindingResult().getFieldErrors().forEach(error -> {
				errors.put(error.getField(), error.getDefaultMessage());
			});
			return Map.of("error", errors);
		}
	
		@ExceptionHandler(UsernameNotFoundException.class)
		@ResponseStatus(HttpStatus.UNAUTHORIZED)
		public Map<String, Object> handle(UsernameNotFoundException ex) {
			return Map.of("error", ex.getMessage());
		}
	
		@ExceptionHandler(DataNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		public Map<String, Object> handle(DataNotFoundException ex) {
			return Map.of("error", ex.getMessage());
		}
	
		@ExceptionHandler(HttpMessageNotReadableException.class)
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		public Map<String, Object> handle(HttpMessageNotReadableException ex) {
			return Map.of("error", "Enter in correct format");
		}
	
		@ExceptionHandler(IllegalArgumentException.class)
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		public Map<String, Object> handle(IllegalArgumentException ex) {
			return Map.of("error", ex.getMessage());
		}
	
		@ExceptionHandler(NoResourceFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		public Map<String, Object> handle(NoResourceFoundException ex) {
			return Map.of("error", "You have Entered Wrong URL");
		}
	
		@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
		@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
		public Map<String, Object> handle(HttpRequestMethodNotSupportedException ex) {
			return Map.of("error", "You have Selected Wrong Method");
		}
	
		@ExceptionHandler(AccessDeniedException.class)
		@ResponseStatus(HttpStatus.FORBIDDEN)
		public Map<String, Object> handle(AccessDeniedException ex) {
			return Map.of("error", "You can not Access this Resource");
		}
	
		@ExceptionHandler(DisabledException.class)
		@ResponseStatus(HttpStatus.FORBIDDEN)
		public Map<String, Object> handle(DisabledException ex) {
			return Map.of("error", "Your Account is Blocked Contact Admin");
		}
	
		@ExceptionHandler(BadCredentialsException.class)
		@ResponseStatus(HttpStatus.FORBIDDEN)
		public Map<String, Object> handle(BadCredentialsException ex) {
			return Map.of("error", "Invalid Password");
		}
	
		@ExceptionHandler(ExpiredJwtException.class)
		@ResponseStatus(HttpStatus.FORBIDDEN)
		public Map<String, Object> handle(ExpiredJwtException ex) {
			return Map.of("error", "Token Expired");
		}
	
		@ExceptionHandler(MalformedJwtException.class)
		@ResponseStatus(HttpStatus.FORBIDDEN)
		public Map<String, Object> handle(MalformedJwtException ex) {
			return Map.of("error", "Invalid Token");
		}
	
	}
