package com.epam.user.exception;

public class NoSuchUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSuchUserException(String message) {
		super(message);
	}

}
