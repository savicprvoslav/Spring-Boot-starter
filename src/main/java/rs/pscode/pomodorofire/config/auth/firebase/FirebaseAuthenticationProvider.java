package rs.pscode.pomodorofire.config.auth.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import rs.pscode.pomodorofire.service.impl.UserServiceImpl;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Qualifier(value = UserServiceImpl.NAME)
	private UserDetailsService userService;

	public boolean supports(Class<?> authentication) {
		return (PomodoroAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}

		PomodoroAuthenticationToken authenticationToken = (PomodoroAuthenticationToken) authentication;
		UserDetails details = userService.loadUserByUsername(authenticationToken.getName());

		// TODO what if user is not here?

		authenticationToken = new PomodoroAuthenticationToken(details, authentication.getCredentials(),
				details.getAuthorities());

		return authenticationToken;
	}

}
