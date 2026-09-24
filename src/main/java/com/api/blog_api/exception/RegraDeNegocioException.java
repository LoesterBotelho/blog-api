package com.api.blog_api.exception;

public class RegraDeNegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegraDeNegocioException(String message) {
        super(message);
    }
}