package edu.wm.cs420;

import java.security.Principal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wm.cs420.domain.Meeting;
import edu.wm.cs420.domain.Role;
import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.exceptions.CodedException;
import edu.wm.cs420.exceptions.DuplicateUserException;
import edu.wm.cs420.exceptions.NoUserFoundException;
import edu.wm.cs420.service.MapService;
import edu.wm.cs420.service.SecurityService;
import edu.wm.cs420.service.UserService;

@Controller
public class HomeController {
	
	@Autowired MapService mapService;
	@Autowired SecurityService securityService;
	@Autowired UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String home() {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		String formattedDate = dateFormat.format(date);
		
		return formattedDate;
	}
	
	/* =============== ADMIN =============== */
	@RequestMapping(value = "/admin/update", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse updateAdmin() {
		
		logger.info("In admin update.");
		Set<Role> roles = new HashSet<Role>();
		roles.add(Role.ROLE_USER);
		roles.add(Role.ROLE_ADMIN);
		
		FullUser ethan = new FullUser("Ethan", "Roday", "erroday",
				"123456", roles, null, null, new Date().getTime(),
				false);
		userService.updateUser(ethan);
		logger.info("Updated Ethan.");
		return new JSONResponse(0,null);
	
	}
	
	@RequestMapping(value = "/admin/insert", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse insertAdmin() {
		
		List<FullUser> users = new ArrayList<FullUser>();
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(Role.ROLE_USER);
		
		users.add(new FullUser("Carolyn", "McKenna", "camckenna",
				"123456", roles, null, null, new Date().getTime(),
				false));
		
		users.add(new FullUser("Jessica", "Chen", "jlchen",
				"123456", roles, null, null, new Date().getTime(),
				false));
		
		users.add(new FullUser("Brittany", "Reynoso", "bgreynoso",
				"123456", roles, null, null, new Date().getTime(),
				false));
		
		users.add(new FullUser("Jaleo", "Velasco-Madden", "jnvelascomadde",
				"123456", roles, null, null, new Date().getTime(),
				false));
		
		users.add(new FullUser("Aslyn", "Blohm", "aablohm",
				"123456", roles, null, null, new Date().getTime(),
				false));
		
		roles.add(Role.ROLE_ADMIN);
		users.add(new FullUser("Ethan", "Roday", "erroday",
				"123456", roles, null, null, new Date().getTime(),
				false));
		
		try {
			for(FullUser u : users) {
				userService.insertUser(u);
			}
			return new JSONResponse(0,null);
		} catch (DuplicateUserException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/clear", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse clearUsers() {
		userService.clearUsers();
		return new JSONResponse(0,null);
	}
	
	
	/* =============== REGISTRATION =============== */ 
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONResponse registerUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("emailHandle") String emailHandle, @RequestParam("password") String password,
			@RequestParam("lat") String latStr, @RequestParam("lng") String lngStr) {
			
		logger.info("In registration for "+emailHandle);
		Set<Role> roles = new HashSet<Role>();
		roles.add(Role.ROLE_USER);
		double[] location = {Double.parseDouble(lngStr),Double.parseDouble(latStr)};
		FullUser u = new FullUser(firstName,lastName,emailHandle,password,
				roles,null,location,new Date().getTime(),false);
		
		try {
			userService.insertUser(u);
			return new JSONResponse(0,u);
		} catch (DuplicateUserException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	/* =============== USER DETAILS =============== */
	
	@RequestMapping(value = "/user/me", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getMe(Principal principal) {
			
		try {
			return new JSONResponse(0,userService.getUserByEmailHandle(principal.getName()));
		} catch (NoUserFoundException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	/* =============== REQUESTS =============== */
	
	@RequestMapping(value = "/user/me/requestsForMe", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getRequestsForMe(Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			return new JSONResponse(0,userService.getPendingRequestsForUser(me));
		} catch (NoUserFoundException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/me/requestsFromMe", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getRequestsFromMe(Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			return new JSONResponse(0,userService.getPendingRequestsFromUser(me));
		} catch (NoUserFoundException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/me/makeRequest", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONResponse makeRequest(@RequestParam("emailHandle") String emailHandle, Principal principal) {
		
		FullUser me;
		FullUser friend;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			friend = userService.getUserByEmailHandle(emailHandle);
			userService.makeRequest(me,friend);
			return new JSONResponse();
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/me/acceptRequest", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONResponse acceptRequest(@RequestParam("emailHandle") String emailHandle, Principal principal) {
		
		FullUser me;
		FullUser friend;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			friend = userService.getUserByEmailHandle(emailHandle);
			userService.acceptRequest(friend, me);
			return new JSONResponse();
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}


	@RequestMapping(value = "/user/me/denyRequest", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONResponse denyRequest(@RequestParam("emailHandle") String emailHandle, Principal principal) {
		
		FullUser me;
		FullUser friend;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			friend = userService.getUserByEmailHandle(emailHandle);
			userService.denyRequest(friend, me);
			return new JSONResponse();
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	/* =============== CONNECTED USERS =============== */
	
	@RequestMapping(value = "/user/me/connections", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getMyConnections(Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			return new JSONResponse(0,userService.getConnectionsForUser(me));
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/me/connections/regex", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getMyConnectionsRegex(@RequestParam("regex") String regex,Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			return new JSONResponse(0,userService.getConnectionsForUserWithRegex(me,regex));
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	/* =============== UNCONNECTED USERS =============== */
	
	@RequestMapping(value = "/user/regex", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getUsersRegex(@RequestParam("regex") String regex) {
		
		try {
			return new JSONResponse(0,userService.getPublicUsersWithRegex(regex));
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	/* =============== LOCATIONS =============== */
	
	@RequestMapping(value = "/user/me/nearby", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONResponse getNearbyToMe(@RequestParam("lat") String latStr, @RequestParam("lng") String lngStr,
			Principal principal) {
		
		FullUser me;
		try {
			//Update my location
			me = userService.getUserByEmailHandle(principal.getName());
			double[] location = {Double.parseDouble(lngStr),Double.parseDouble(latStr)};
			me.setLocation(location);
			me.setLastUpdate(new Date().getTime());
			logger.info(location.toString());
			userService.updateUser(me);
			logger.info(userService.getUserByEmailHandle(principal.getName()).getLocation().toString());
			//Get nearby users
			return new JSONResponse(0,userService.getNearbyUsers(me, 200));
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/me/shareLocation", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONResponse shareLocation(@RequestParam("lat") String latStr, @RequestParam("lng") String lngStr,
			Principal principal) {
		
		FullUser me;
		try {
			//Update my location
			me = userService.getUserByEmailHandle(principal.getName());
			double[] location = {Double.parseDouble(lngStr),Double.parseDouble(latStr)};
			me.setLocation(location);
			me.setLastUpdate(new Date().getTime());
			
			//Set sharing to true
			me.setSharingLocation(true);
			logger.info(location[0]+" "+location[1]);
			userService.updateUser(me);
			double[] toLog = userService.getUserByEmailHandle(principal.getName()).getLocation();
			logger.info(toLog[0]+" "+toLog[1]);
			
			return new JSONResponse(0,null);
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/me/stopSharingLocation", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONResponse stopSharingLocation(Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			me.setSharingLocation(false);
			userService.updateUser(me);
			return new JSONResponse(0,null);
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/location/nearby", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getNearbyToLocation(@RequestParam("lat") String latStr,
			@RequestParam("lng") String lngStr, Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			return new JSONResponse(0,mapService.getNearbyUsersToLocation(me, 
					Double.parseDouble(latStr),	Double.parseDouble(lngStr),	200));
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	/* =============== MEETINGS =============== */
	@RequestMapping(value = "/meetings/makeMeeting", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONResponse makeMeeting(@RequestParam("lat") String latStr, 
			@RequestParam("lng") String lngStr, @RequestParam("time") long time,
			@RequestParam(value = "invited") String invited, Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			double[] location = {Double.parseDouble(lngStr),Double.parseDouble(latStr)};
			mapService.insertMeeting(new Meeting(location,time,me.getEmailHandle(),
					Arrays.asList(invited.split(","))));
			return new JSONResponse(0,null);
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		} catch (Exception e) {
			logger.info(e.toString());
			return new JSONResponse(6,e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/me/meetings", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getMyMeetings(Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			return new JSONResponse(0,mapService.
					getMeetingsByOwnerEmailHandle(me.getEmailHandle()));
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/connections/meetings", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getMyConnectionsMeetings(Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());
			List<Meeting> meetings = new ArrayList<Meeting>();
			for (String emailHandle : me.getFriendsEmailHandles()) {
				meetings.addAll(mapService.getMeetingsByOwnerEmailHandle(emailHandle));
			}
			return new JSONResponse(0,meetings);
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/user/me/invitedMeetings", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public JSONResponse getMyInvitedMeetings(Principal principal) {
		
		FullUser me;
		try {
			me = userService.getUserByEmailHandle(principal.getName());			
			return new JSONResponse(0,userService.getInvitedMeetings(me));
		} catch (CodedException e) {
			logger.info(e.toString());
			return new JSONResponse(e.getErrorCode(),e.getMessage());
		}
		
	}
	
}	