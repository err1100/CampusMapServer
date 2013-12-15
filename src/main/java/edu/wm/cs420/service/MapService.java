package edu.wm.cs420.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.wm.cs420.dao.IMeetingDAO;
import edu.wm.cs420.dao.IUserDAO;
import edu.wm.cs420.domain.ConnectedUser;
import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.domain.Meeting;

public class MapService {
	
	@Autowired IMeetingDAO meetingDAO;
	@Autowired IUserDAO userDAO;

	public void insertMeeting(Meeting m) {
		meetingDAO.insertMeeting(m);
	}
	
	public void deleteMeeting(Meeting m) {
		meetingDAO.deleteMeeting(m);
	}
	
	public List<Meeting> getMeetingsByOwnerEmailHandle(String emailHandle) {
		return meetingDAO.getMeetingsByOwnerEmailHandle(emailHandle);
	}
	
	public List<ConnectedUser> getNearbyUsersToLocation(FullUser u, double lat, double lng, int dist) {
		List<ConnectedUser> nearby = new ArrayList<ConnectedUser>();
		for (FullUser near : userDAO.getNearbyPlayers(lat, lng, dist)) {
			if (u.getFriendsEmailHandles().contains(near.getEmailHandle())
					&& near.isSharingLocation()) {
				nearby.add(new ConnectedUser(near));
			}
		}
		return nearby;
	}
}
