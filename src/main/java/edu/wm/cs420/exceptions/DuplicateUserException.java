package edu.wm.cs420.exceptions;

public class DuplicateUserException extends CodedException {
	
	private String username;
	
	private static final long serialVersionUID = 1L;

	public DuplicateUserException(String userID) {
		super();
		this.username = userID;
		this.errorCode = 1;
	}
	
	public String getUserID() {
		return username;
	}
	
	public String getMessage() {
		return "A user with username "+username+" already exists";
	}
}
