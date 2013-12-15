package edu.wm.cs420.domain;

public class Meeting {
	
	private String id;
	private double[] location;
	private long time;
	private String ownerEmailHandle;
	
	public Meeting(double[] location, long time, String ownerEmailHandle) {
		super();
		this.location = location;
		this.time = time;
		this.ownerEmailHandle = ownerEmailHandle;
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

}
