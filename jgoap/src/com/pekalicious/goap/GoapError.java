package com.pekalicious.goap;

class GoapError extends Error {
	private static final long serialVersionUID = 2707000073965574967L;

	public GoapError(String message) {
		super(message);
	}
}