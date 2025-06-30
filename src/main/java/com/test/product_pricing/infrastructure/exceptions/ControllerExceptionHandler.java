package com.test.product_pricing.infrastructure.exceptions;

import com.test.product_pricing.domain.exceptions.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(PriceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handlePriceNotFoundException(PriceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(), request.getDescription(false)
		);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorMessage> handleMissingParam(MissingServletRequestParameterException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(), new Date(),
				"Falta el parámetro obligatorio: '" + ex.getParameterName() + "'.",
				request.getDescription(false)
		);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorMessage> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(), new Date(),
				"El parámetro '" + ex.getName() + "' tiene un valor inválido.",
				request.getDescription(false)
		);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
}
