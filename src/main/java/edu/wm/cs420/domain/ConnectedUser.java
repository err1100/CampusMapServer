package edu.wm.cs420.domain;

import java.util.HashSet;
import java.util.Set;

public final class ConnectedUser {

	private String firstName;
	private String lastName;
	private String emailHandle;
	private double[] location;
	private Set<String> friendsEmailHandles;
	private boolean isSharingLocation;
	
	public ConnectedUser() {
		friendsEmailHandles = new HashSet<String>();
		location = new double[2];
	}
	
	public ConnectedUser(FullUser u) {
		 firstName = u.getFirstName();
		 lastName = u.getLastName();
		 emailHandle = u.getEmailHandle();
		 friendsEmailHandles = u.getFriendsEmailHandles();
		 location = u.getLocation();
		 isSharingLocation = u.isSharingLocation();
	}

	public ConnectedUser(String firstName, String lastName, String emailHandle,
			Set<String> friends, double[] location,
			boolean isSharingLocation) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailHandle = emailHandle;
		this.location = location;
		this.friendsEmailHandles = (friends == null) ? new HashSet<String>() : friends;
		this.isSharingLocation = isSharingLocation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailHandle() {
		return emailHandle;
	}

	public void setEmailHandle(String emailHandle) {
		this.emailHandle = emailHandle;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
	}
	
	public double getLatitude() {
		return this.location[1];
	}
	
	public double getLongitude() {
		return this.location[0];
	}

	public Set<String> getFriendsEmailHandles() {
		return friendsEmailHandles;
	}

	public void setFriendsEmailHandles(Set<String> friendsEmailHandles) {
		this.friendsEmailHandles = friendsEmailHandles;
	}

	public boolean isSharingLocation() {
		return isSharingLocation;
	}

	public void setSharingLocation(boolean isSharingLocation) {
		this.isSharingLocation = isSharingLocation;
	}

}
