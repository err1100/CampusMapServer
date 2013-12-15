package edu.wm.cs420.exceptions;

public abstract class CodedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	protected int errorCode;
	
	public int getErrorCode() {
		return errorCode;
	}

}
