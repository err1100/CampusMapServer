package edu.wm.cs420.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.exceptions.DuplicateUserException;
import edu.wm.cs420.exceptions.NoUserFoundException;

public class MongoUserDAO implements IUserDAO {

	@Autowired private MongoOperations mongoTemplate;
	
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
		return getNearbyPlayers(u.getLatitude(), u.getLongitude(), dist);
	}

	@Override
	public List<FullUser> getNearbyPlayers(double lat, double lng, double dist) {
		NearQuery geoNear = NearQuery.near(lng, lat,
				Metrics.KILOMETERS).maxDistance(dist/1000);
		GeoResults<FullUser> geoUsers = mongoTemplate.geoNear(geoNear, FullUser.class);
		List<FullUser> users = new ArrayList<FullUser>();
		for (GeoResult<FullUser> geoUser : geoUsers) {
			users.add(geoUser.getContent());
		}
		users.remove(0); //Remove p (always first because GeoResults are sorted)
		return users;
	}

}
