package edu.wm.cs420.exceptions;

public class RequestAlreadyAcceptedException extends CodedException {

	private static final long serialVersionUID = 1L;
	
	private String fromUserEmailHandle;
	private String toUserEmailHandle;

	public RequestAlreadyAcceptedException(String fromUserEmailHandle, String toUserEmailHandle) {
		this.fromUserEmailHandle = fromUserEmailHandle;
		this.toUserEmailHandle = toUserEmailHandle;
		this.errorCode = 5;
	}
	
	public String getFromUserEmailHandle() {
		return fromUserEmailHandle;
	}
	public String getToUserEmailHandle() {
		return toUserEmailHandle;
	}
	public String getMessage() {
		return "A request from "+this.fromUserEmailHandle+" to "+
				this.toUserEmailHandle+" has already been accepted!";
	}
	
}
