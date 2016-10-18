package rs.pscode.pomodorofire.config.auth.firebase;

import java.io.IOException;
import java.util.Random;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class FirebaseFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String xAuth = request.getHeader("X-Authorization-Firebase");

		// validate the value in xAuth
		if (!isValid(xAuth)) {
			throw new SecurityException();
		}

		// The token is 'valid' so magically get a user id from it
		String userName = getUserIdFromToken(xAuth);

		// Create our Authentication and let Spring know about it
		Authentication auth = new PomodoroAuthenticationToken(userName, xAuth);
		SecurityContextHolder.getContext().setAuthentication(auth);

		filterChain.doFilter(request, response);
	}

	private Boolean isValid(String token) {
		return new Random().nextBoolean();
	}

	private String getUserIdFromToken(String token) {
		return "admin";
	}
}
