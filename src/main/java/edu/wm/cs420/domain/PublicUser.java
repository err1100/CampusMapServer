package edu.wm.cs420.domain;

public final class PublicUser {

	private String firstName;
	private String lastName;
	private String emailHandle;
	
	public PublicUser() {}
	
	public PublicUser(FullUser u) {
		firstName = u.getFirstName();
		lastName = u.getLastName();
		emailHandle = u.getEmailHandle();
	}

	public PublicUser(String firstName, String lastName, String emailHandle) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailHandle = emailHandle;
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

}
