package com.mtlogic.x12.exception;

public class InvalidX12MessageException extends Exception {
	private static final long serialVersionUID = 9216006553718942042L;

	public InvalidX12MessageException(String message) {
		super(message);
	}
	
	public InvalidX12MessageException(Throwable cause) {
        super(cause);
    }
	
	public InvalidX12MessageException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
