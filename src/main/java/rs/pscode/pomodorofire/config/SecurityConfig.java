package rs.pscode.pomodorofire.config;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rs.pscode.pomodorofire.config.auth.firebase.FirebaseAuthenticationProvider;
import rs.pscode.pomodorofire.config.auth.firebase.FirebaseFilter;
import rs.pscode.pomodorofire.service.FirebaseService;
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

		@Value("${rs.pscode.firebase.enabled}")
		private Boolean firebaseEnabled;

		@Autowired
		private FirebaseAuthenticationProvider firebaseProvider;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService);
			if (firebaseEnabled) {
				auth.authenticationProvider(firebaseProvider);
			}
		}
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Value("${rs.pscode.firebase.enabled}")
		private Boolean firebaseEnabled;

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
					"/configuration/security", "/swagger-ui.html", "/webjars/**", "/v2/swagger.json");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			if (firebaseEnabled) {
				http.addFilterBefore(tokenAuthorizationFilter(), BasicAuthenticationFilter.class).authorizeRequests()//

						.antMatchers("/api/open/**").hasAnyRole(Roles.ANONYMOUS)//
						.antMatchers("/api/client/**").hasRole(Roles.USER)//
						.antMatchers("/api/admin/**").hasAnyRole(Roles.ADMIN)//
						.antMatchers("/health/**").hasAnyRole(Roles.ADMIN)//
						.antMatchers("/**").denyAll()//
						.and().csrf().disable()//
						.anonymous().authorities(Roles.ROLE_ANONYMOUS);//
			} else {
				http.httpBasic().and().authorizeRequests()//

						.antMatchers("/api/open/**").hasAnyRole(Roles.ANONYMOUS)//
						.antMatchers("/api/client/**").hasRole(Roles.USER)//
						.antMatchers("/api/admin/**").hasAnyRole(Roles.ADMIN)//
						.antMatchers("/health/**").hasAnyRole(Roles.ADMIN)//
						.antMatchers("/**").denyAll()//
						.and().csrf().disable()//
						.anonymous().authorities(Roles.ROLE_ANONYMOUS);//
			}
		}

		@Autowired(required = false)
		private FirebaseService firebaseService;

		private FirebaseFilter tokenAuthorizationFilter() {
			return new FirebaseFilter(firebaseService);
		}

	}
}
