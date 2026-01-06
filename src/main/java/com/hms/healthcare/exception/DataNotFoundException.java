package com.hms.healthcare.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
