package edu.wm.cs420.dao;

import java.util.List;

import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.exceptions.DuplicateUserException;
import edu.wm.cs420.exceptions.NoUserFoundException;

public interface IUserDAO {
	
	public void insertUser(FullUser user) throws DuplicateUserException;
	public void updateUser(FullUser user);
	public FullUser getUserByEmailHandle(String emailHandle) throws NoUserFoundException;
	public List<FullUser> getAllUsers();
	public List<FullUser> getUsersByRegex(String regex);
	public List<FullUser> getNearbyPlayers(FullUser u, double dist);
	public List<FullUser> getNearbyPlayers(double lat, double lng, double dist);
	public void removeAllUsersExcept(FullUser u);
	public void removeAllUsers();
	public void removeUser(FullUser u);
	
}
