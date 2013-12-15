package edu.wm.cs420.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public final class FullUser {

	private String firstName;
	private String lastName;
	private String emailHandle;
	private String password;
	private double[] location;
	private long lastUpdate;
	private Set<Role> roles;
	private Set<String> friendsEmailHandles;
	private boolean isSharingLocation;
	
	public FullUser() {
		roles = new HashSet<Role>();
		friendsEmailHandles = new HashSet<String>();
		location = new double[2];
	}

	public FullUser(String firstName, String lastName, String emailHandle,
			String password, Set<Role> roles, Set<String> friends, double[] location,
			long lastUpdate, boolean isSharingLocation) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailHandle = emailHandle;
		this.password = password;
		this.location = (location == null) ? new double[2] : location;
		this.roles = (roles == null) ? new HashSet<Role>() : roles;
		this.friendsEmailHandles = (friends == null) ? new HashSet<String>() : friends;
		this.lastUpdate = lastUpdate;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
	}
	
	public double getLatitude() {
		return this.location[0];
	}
	
	public double getLongitude() {
		return this.location[1];
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
