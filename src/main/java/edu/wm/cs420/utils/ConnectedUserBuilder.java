package edu.wm.cs420.utils;

import edu.wm.cs420.domain.ConnectedUser;
import edu.wm.cs420.domain.FullUser;

public class ConnectedUserBuilder {

	public ConnectedUser getConnectedUserFromFullUser(FullUser u) {
		return new ConnectedUser();
	}
}
