package edu.wm.cs420.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import edu.wm.cs420.dao.IConnectionRequestDAO;
import edu.wm.cs420.dao.IUserDAO;
import edu.wm.cs420.domain.ConnectedUser;
import edu.wm.cs420.domain.ConnectionRequest;
import edu.wm.cs420.domain.ConnectionRequestState;
import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.domain.PublicUser;
import edu.wm.cs420.exceptions.DuplicateRequestException;
import edu.wm.cs420.exceptions.DuplicateUserException;
import edu.wm.cs420.exceptions.NoRequestFoundException;
import edu.wm.cs420.exceptions.NoUserFoundException;
import edu.wm.cs420.exceptions.RequestAlreadyAcceptedException;

public class UserService {
	
	@Autowired IUserDAO userDAO;
	@Autowired IConnectionRequestDAO requestDAO;
	
	public void insertUser(FullUser u) throws DuplicateUserException {
		userDAO.insertUser(u);
	}
	
	public void updateUser(FullUser u) {
		userDAO.updateUser(u);
	}
	
	public void clearUsers() {
		userDAO.removeAllUsers();
	}
	
	public FullUser getUserByEmailHandle(String emailHandle) throws NoUserFoundException {
		return userDAO.getUserByEmailHandle(emailHandle);
	}
	
	public List<FullUser> getAllUsers() {
		return userDAO.getAllUsers();
	}
	
	public List<ConnectionRequest> getPendingRequestsForUser(FullUser u) {
		return requestDAO.getPendingConnectionRequestsForUser(u);
	}
	
	public List<ConnectionRequest> getPendingRequestsFromUser(FullUser u) {
		return requestDAO.getPendingConnectionRequestsByUser(u);
	}
	
	public void makeRequest(FullUser from, FullUser to) throws DuplicateRequestException {
		requestDAO.insertConnectionRequest(new ConnectionRequest(from.getEmailHandle(),to.getEmailHandle()));
	}
	
	public void acceptRequest(FullUser from, FullUser to) throws NoRequestFoundException {
		
		//Accept the request
		//We don't really care about the state before; a request can always be accepted
		ConnectionRequest r = requestDAO.getRequestByToAndFrom(from.getEmailHandle(), to.getEmailHandle());
		r.setState(ConnectionRequestState.ACCEPTED);
		requestDAO.updateConnectionRequest(r);
		
		//Add to friends
		from.getFriendsEmailHandles().add(from.getEmailHandle());
		to.getFriendsEmailHandles().add(to.getEmailHandle());
		
	}
	
	public void denyRequest(FullUser from, FullUser to) throws NoRequestFoundException, RequestAlreadyAcceptedException {
		
		//Get the request
		ConnectionRequest r = requestDAO.getRequestByToAndFrom(from.getEmailHandle(), to.getEmailHandle());
		
		//Ensure it hasn't already been accepted
		if (r.getState() == ConnectionRequestState.ACCEPTED) {
			throw new RequestAlreadyAcceptedException(from.getEmailHandle(),to.getEmailHandle());
		}
		
		//Deny the request
		r.setState(ConnectionRequestState.DENIED);
		requestDAO.updateConnectionRequest(r);
	}

	public List<ConnectedUser> getConnectionsForUser(FullUser me) throws NoUserFoundException {
		List<ConnectedUser> connections = new ArrayList<ConnectedUser>();
		for (String friendEmailHandle : me.getFriendsEmailHandles()) {
			connections.add(new ConnectedUser(userDAO.getUserByEmailHandle(friendEmailHandle)));
		}
		return connections;
	}
	
	public List<ConnectedUser> getConnectionsForUserWithRegex(FullUser me, String regex) throws NoUserFoundException {
		List<ConnectedUser> connections = new ArrayList<ConnectedUser>();
		for (String friendEmailHandle : me.getFriendsEmailHandles()) {
			if (Pattern.matches(regex, friendEmailHandle)) {
				connections.add(new ConnectedUser(userDAO.getUserByEmailHandle(friendEmailHandle)));
			}
		}
		return connections;
	}
	
	public List<PublicUser> getPublicUsersWithRegex(String regex) throws NoUserFoundException {
		List<PublicUser> users = new ArrayList<PublicUser>();
		for (FullUser u : userDAO.getUsersByRegex(regex)) {
			users.add(new PublicUser(u));
		}
		return users;
	}

	public List<ConnectedUser> getNearbyUsers(FullUser u, int dist) {
		List<ConnectedUser> nearby = new ArrayList<ConnectedUser>();
		for (FullUser near : userDAO.getNearbyPlayers(u, dist)) {
			if (u.getFriendsEmailHandles().contains(near.getEmailHandle())
					&& near.isSharingLocation()) {
				nearby.add(new ConnectedUser(near));
			}
		}
		return nearby;
	}
	
}
