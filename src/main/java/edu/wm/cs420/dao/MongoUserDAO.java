package edu.wm.cs420.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.CommandResult;

import edu.wm.cs420.HomeController;
import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.exceptions.DuplicateUserException;
import edu.wm.cs420.exceptions.NoUserFoundException;

public class MongoUserDAO implements IUserDAO {

	@Autowired private MongoOperations mongoTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(MongoUserDAO.class);
	
	@Override
	public void insertUser(FullUser user) throws DuplicateUserException {
		Query q = new Query(Criteria.where("emailHandle").is(user.getEmailHandle()));
		if (mongoTemplate.find(q, FullUser.class).size() != 0) {
			throw new DuplicateUserException(user.getEmailHandle());
		}
		mongoTemplate.save(user);
	}

	@Override
	public List<FullUser> getAllUsers() {
		return mongoTemplate.find(new Query(), FullUser.class);
	}

	public List<FullUser> getUsersByRegex(String regex) {
		Query q = new Query(Criteria.where("emailHandle").regex(regex));
		return mongoTemplate.find(q, FullUser.class);
	}
	
	@Override
	public void updateUser(FullUser user) {
		logger.info("In DAO update.");
		mongoTemplate.save(user);
	}


	@Override
	public void removeAllUsersExcept(FullUser u) {
		removeAllUsers();
		mongoTemplate.save(u);
	}
	
	@Override
	public void removeAllUsers() {
		mongoTemplate.remove(new Query(), FullUser.class);
	}

	@Override
	public FullUser getUserByEmailHandle(String emailHandle) throws NoUserFoundException {
		Query q = new Query(Criteria.where("emailHandle").is(emailHandle));
		FullUser u = mongoTemplate.findOne(q, FullUser.class);
		if (u == null) {
			throw new NoUserFoundException(emailHandle);
		}
		return u;
	}

	@Override
	public void removeUser(FullUser u) {
		Query q = new Query(Criteria.where("emailHandle").is(u.getEmailHandle()));
		mongoTemplate.remove(q, FullUser.class);
	}

	@Override
	public List<FullUser> getNearbyPlayers(FullUser u, double dist) {
		logger.info("in getNearbyPlayers() for "+u.getEmailHandle());
		List<FullUser> users = getNearbyPlayers(u.getLatitude(), u.getLongitude(), dist);
		/*
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getEmailHandle() == u.getEmailHandle()) {
				users.remove(i);
				break;
			}
		} //We are assured that this user will be in the GeoResults. We have to iterate
		//(rather than just remove the first user) in case two users have the same position
		*/
		logger.info("Back in getNearbyPlayers(). Length of nearby list is "+users.size());
		return users;
	}

	@Override
	public List<FullUser> getNearbyPlayers(double lat, double lng, double dist) {
		
		logger.info("in getNearbyPlayers() with "+lng+","+lat);
		
		Point p = new Point(lng,lat);
		NearQuery geoNear = NearQuery.near(p).maxDistance(new Distance(dist/1000, Metrics.KILOMETERS));
		GeoResults<FullUser> geoUsers = mongoTemplate.geoNear(geoNear, FullUser.class);
		logger.info("Size of geoUsers is "+geoUsers.getContent().size());
		List<FullUser> users = new ArrayList<FullUser>();
		for (GeoResult<FullUser> geoUser : geoUsers) {
			users.add(geoUser.getContent());
		}
		return users;
	}
}
