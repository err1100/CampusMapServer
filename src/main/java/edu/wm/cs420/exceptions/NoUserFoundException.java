package edu.wm.cs420.exceptions;

public class NoUserFoundException extends CodedException {
	
	private String emailHandle;
	
	private static final long serialVersionUID = 1L;

	public NoUserFoundException(String emailHandle) {
		super();
		this.emailHandle = emailHandle;
		this.errorCode = 2;
	}
	
	public String getEmailHandle() {
		return this.emailHandle;
	}
	
	public String getMessage() {
		return "No user named "+this.emailHandle+" was found.";
	}

}
