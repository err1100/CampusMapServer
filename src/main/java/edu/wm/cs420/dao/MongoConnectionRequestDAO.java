package edu.wm.cs420.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import edu.wm.cs420.domain.ConnectionRequest;
import edu.wm.cs420.domain.ConnectionRequestState;
import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.exceptions.DuplicateRequestException;
import edu.wm.cs420.exceptions.NoRequestFoundException;

public class MongoConnectionRequestDAO implements IConnectionRequestDAO {

	@Autowired private MongoOperations mongoTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(MongoUserDAO.class);
	
	@Override
	public void insertConnectionRequest(ConnectionRequest r) throws DuplicateRequestException {
		if (mongoTemplate.find(getToAndFromQuery(r.getToUserEmailHandle(),r.getFromUserEmailHandle()), 
				ConnectionRequest.class).size() != 0) {
			throw new DuplicateRequestException(r.getFromUserEmailHandle(),r.getToUserEmailHandle());
		}
		mongoTemplate.save(r);
	}

	@Override
	public void updateConnectionRequest(ConnectionRequest r) {
		logger.info("In connection request update for "+r.getFromUserEmailHandle()+" to "+r.getToUserEmailHandle());
		mongoTemplate.save(r);
	}

	@Override
	public ConnectionRequest getRequestByToAndFrom(String toEmailHandle,
			String fromEmailHandle) throws NoRequestFoundException {
		Query q = getToAndFromQuery(toEmailHandle,fromEmailHandle);
		ConnectionRequest r = mongoTemplate.findOne(q, ConnectionRequest.class);
		if (r == null) {
			throw new NoRequestFoundException(fromEmailHandle,toEmailHandle);
		}
		return r;
	}

	@Override
	public List<ConnectionRequest> getPendingConnectionRequestsByUser(
			FullUser u) {
		return mongoTemplate.find(getToQuery(u.getEmailHandle()), ConnectionRequest.class);
	}

	@Override
	public List<ConnectionRequest> getPendingConnectionRequestsForUser(
			FullUser u) {
		return mongoTemplate.find(getFromQuery(u.getEmailHandle()), ConnectionRequest.class);
	}

	@Override
	public void removeConnectionRequest(ConnectionRequest r) {
		mongoTemplate.remove(r);
	}
	
	private Query getToAndFromQuery(String toEmailHandle, String fromEmailHandle) {
		return new Query(Criteria.where("fromUserEmailHandle").is(fromEmailHandle)
				.andOperator(Criteria.where("toUserEmailHandle").is(toEmailHandle)));
	}
	
	private Query getToQuery(String toEmailHandle) {
		return new Query(Criteria.where("toUserEmailHandle").is(toEmailHandle)
				.andOperator(Criteria.where("state").is(ConnectionRequestState.PENDING)));
	}
	
	private Query getFromQuery(String fromEmailHandle) {
		return new Query(Criteria.where("fromUserEmailHandle").is(fromEmailHandle)
				.andOperator(Criteria.where("state").is(ConnectionRequestState.PENDING)));
	}

	@Override
	public void removeAll() {
		mongoTemplate.remove(new Query(), ConnectionRequest.class);
	}

}
