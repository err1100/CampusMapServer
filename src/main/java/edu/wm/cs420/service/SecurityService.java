package edu.wm.cs420.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.wm.cs420.domain.Role;
import edu.wm.cs420.domain.FullUser;
import edu.wm.cs420.exceptions.NoUserFoundException;

public class SecurityService implements UserDetailsService {
	
	//private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	@Autowired UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		FullUser u;
		try {
			u = userService.getUserByEmailHandle(username);
		} catch (NoUserFoundException e) {
			throw new UsernameNotFoundException(username);
		}

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new org.springframework.security.core.userdetails.User(
				u.getEmailHandle(),
				u.getPassword(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(u));
	}

	public Collection<SimpleGrantedAuthority> getAuthorities(FullUser u) {
		return getGrantedAuthorities(u);
	}

	public List<SimpleGrantedAuthority> getGrantedAuthorities(FullUser u) {

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for (Role role : u.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}

		return authorities;
	}

}
