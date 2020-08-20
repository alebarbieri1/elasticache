package br.com.dls.redisclient.utils;

public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String id) {
		super("Could not find customer " + id);
	}

}