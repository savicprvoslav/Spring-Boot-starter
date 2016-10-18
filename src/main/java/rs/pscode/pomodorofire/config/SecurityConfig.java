package rs.pscode.pomodorofire.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rs.pscode.pomodorofire.config.auth.firebase.FirebaseAuthenticationProvider;
import rs.pscode.pomodorofire.config.auth.firebase.FirebaseFilter;
import rs.pscode.pomodorofire.config.auth.firebase.PomodoroAuthenticationToken;
import rs.pscode.pomodorofire.service.impl.UserServiceImpl;

@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	public static class Roles {
		public static final String ANONYMOUS = "ANONYMOUS";
		public static final String USER = "USER";
		static public final String ADMIN = "ADMIN";

		private static final String ROLE_ = "ROLE_";
		public static final String ROLE_ANONYMOUS = ROLE_ + ANONYMOUS;
		public static final String ROLE_USER = ROLE_ + USER;
		static public final String ROLE_ADMIN = ROLE_ + ADMIN;
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		@Qualifier(value = UserServiceImpl.NAME)
		private UserDetailsService userService;

		@Autowired
		private FirebaseAuthenticationProvider firebaseProvider;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService);

			auth.authenticationProvider(firebaseProvider);
		}
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.addFilterBefore(new FirebaseFilter(), BasicAuthenticationFilter.class).httpBasic().and()
					.authorizeRequests()//

					.antMatchers("/api/open/**").hasAnyRole(Roles.ANONYMOUS)//
					.antMatchers("/api/client/**").hasRole(Roles.USER)//
					.antMatchers("/api/admin/**").hasAnyRole(Roles.ADMIN)//
					.antMatchers("/health/**").hasAnyRole(Roles.ADMIN)//
					.antMatchers("/**").denyAll()//
					.and().csrf().disable()//
					.anonymous().authorities(Roles.ROLE_ANONYMOUS);//
		}

	}
}
