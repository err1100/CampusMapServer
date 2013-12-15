package edu.wm.cs420.dao;

import java.util.List;

import edu.wm.cs420.domain.Meeting;

public interface IMeetingDAO {

	public void insertMeeting(Meeting m);
	public void deleteMeeting(Meeting m);
	public List<Meeting> getMeetingsByOwnerEmailHandle(String emailHandle);
	
}