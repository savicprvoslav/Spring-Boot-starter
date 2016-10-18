package rs.pscode.pomodorofire.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rs.pscode.pomodorofire.config.SecurityConfig.Roles;
import rs.pscode.pomodorofire.domain.dao.UserDao;
import rs.pscode.pomodorofire.domain.model.RoleEntity;
import rs.pscode.pomodorofire.domain.model.UserEntity;

@Service(value = UserServiceImpl.NAME)
public class UserServiceImpl implements UserDetailsService {

	public final static String NAME = "UserService";

	@Autowired
	private UserDao userDao;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = userDao.findByUsername(username);
		if (userDetails == null)
			return null;

		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for (GrantedAuthority role : userDetails.getAuthorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		return new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
				userDetails.getPassword(), userDetails.getAuthorities());
	}

	@PostConstruct
	public void init() {
		if (userDao.count() == 0) {
			UserEntity adminEntity = new UserEntity();
			adminEntity.setUsername("admin");
			adminEntity.setPassword("admin");

			adminEntity.setAuthorities(Collections.singletonList(new RoleEntity(Roles.ROLE_ADMIN)));
			userDao.save(adminEntity);

			UserEntity userEntity = new UserEntity();
			userEntity.setUsername("user");
			userEntity.setPassword("user");
			userEntity.setAuthorities(Collections.singletonList(new RoleEntity(Roles.ROLE_USER)));

			userDao.save(userEntity);
		}
	}

}
