package com.api.blog_api.exception;

public class RegistroNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1396421403856570243L;

	public RegistroNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}