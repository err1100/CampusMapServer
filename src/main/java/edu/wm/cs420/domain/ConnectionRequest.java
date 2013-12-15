package edu.wm.cs420.domain;

import java.util.Date;

public class ConnectionRequest {
	
	private String fromUserEmailHandle;
	private String toUserEmailHandle;
	private Date created; //epoch created
	ConnectionRequestState state;

	public ConnectionRequest(String fromUserEmailHandle, String toUserEmailHandle) {
		super();
		this.fromUserEmailHandle = fromUserEmailHandle;
		this.toUserEmailHandle = toUserEmailHandle;
		this.created = new Date();
		this.state = ConnectionRequestState.PENDING;
	}
	
	public ConnectionRequest(String fromUserEmailHandle, String toUserEmailHandle,
			Date created, ConnectionRequestState state) {
		super();
		this.fromUserEmailHandle = fromUserEmailHandle;
		this.toUserEmailHandle = toUserEmailHandle;
		this.created = created;
		this.state = state;
	}

	public String getFromUserEmailHandle() {
		return fromUserEmailHandle;
	}
	public void setFromUserEmailHandle(String fromUserEmailHandle) {
		this.fromUserEmailHandle = fromUserEmailHandle;
	}
	public String getToUserEmailHandle() {
		return toUserEmailHandle;
	}
	public void setToUserEmailHandle(String toUserEmailHandle) {
		this.toUserEmailHandle = toUserEmailHandle;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public ConnectionRequestState getState() {
		return state;
	}
	public void setState(ConnectionRequestState state) {
		this.state = state;
	}
	
}
