package edu.wm.cs420.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import edu.wm.cs420.domain.Meeting;

public class MongoMeetingDAO implements IMeetingDAO {

	@Autowired private MongoOperations mongoTemplate;
	
	@Override
	public void insertMeeting(Meeting m) {
		mongoTemplate.save(m);
	}

	@Override
	public void deleteMeeting(Meeting m) {
		mongoTemplate.remove(m);
	}

	@Override
	public List<Meeting> getMeetingsByOwnerEmailHandle(String emailHandle) {
		Query q = new Query(Criteria.where("ownerEmailHandle").is(emailHandle)
				.and("time").gt(new Date().getTime()));
		List<Meeting> meetings = mongoTemplate.find(q, Meeting.class);
		return meetings;
	}

}
