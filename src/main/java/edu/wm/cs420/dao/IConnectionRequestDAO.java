package edu.wm.cs420.dao;

import java.util.List;

import edu.wm.cs420.domain.ConnectionRequest;
import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.exceptions.DuplicateRequestException;
import edu.wm.cs420.exceptions.NoRequestFoundException;

public interface IConnectionRequestDAO {
	
	public void insertConnectionRequest(ConnectionRequest r) throws DuplicateRequestException;
	public void updateConnectionRequest(ConnectionRequest r);
	public ConnectionRequest getRequestByToAndFrom(String fromEmailHandle, String toEmailHandle) throws NoRequestFoundException;
	public List<ConnectionRequest> getPendingConnectionRequestsByUser(FullUser u);
	public List<ConnectionRequest> getPendingConnectionRequestsForUser(FullUser u);
	public void removeConnectionRequest(ConnectionRequest r);

}
