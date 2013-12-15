package edu.wm.cs420.exceptions;

public class NoRequestFoundException extends CodedException {

	private static final long serialVersionUID = 1L;

	private String fromUserEmailHandle;
	private String toUserEmailHandle;

	public NoRequestFoundException(String fromUserEmailHandle, String toUserEmailHandle) {
		super();
		this.fromUserEmailHandle = fromUserEmailHandle;
		this.toUserEmailHandle = toUserEmailHandle;
		this.errorCode = 4;
	}	
	
	
	public String getFromUserEmailHandle() {
		return fromUserEmailHandle;
	}
	public String getToUseremailHandle() {
		return toUserEmailHandle;
	}
	public String getMessage() {
		return "No request from "+this.fromUserEmailHandle+" to "+
				this.toUserEmailHandle+" was found.";
	}
	
}

