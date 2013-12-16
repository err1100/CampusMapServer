package edu.wm.cs420.domain;

import java.util.List;

public class Meeting {
	
	private String id;
	private double[] location;
	private long time;
	private String ownerEmailHandle;
	private List<String> invitedEmailHandles;
	
	public Meeting(double[] location, long time, String ownerEmailHandle,
			List<String> invitedEmailHandles) {
		super();
		this.location = location;
		this.time = time;
		this.ownerEmailHandle = ownerEmailHandle;
		this.invitedEmailHandles = invitedEmailHandles;
		
	}
	
	public Meeting(String id, double[] location, long time, String ownerEmailHandle) {
		super();
		this.id = id;
		this.location = location;
		this.time = time;
		this.ownerEmailHandle = ownerEmailHandle;
	}
	
	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getOwnerEmailHandle() {
		return ownerEmailHandle;
	}
	public void setOwnerEmailHandle(String ownerEmailHandle) {
		this.ownerEmailHandle = ownerEmailHandle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getInvitedEmailHandles() {
		return invitedEmailHandles;
	}

	public void setInvitedEmailHandles(List<String> invitedEmailHandles) {
		this.invitedEmailHandles = invitedEmailHandles;
	}
	

}
