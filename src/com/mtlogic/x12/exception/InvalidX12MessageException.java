package com.mtlogic.x12.exception;

public class InvalidX12MessageException extends Exception {
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
