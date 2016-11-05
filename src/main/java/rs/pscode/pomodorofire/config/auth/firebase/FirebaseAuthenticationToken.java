package rs.pscode.pomodorofire.config.auth.firebase;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * UsernamePasswordAuthenticationToken
 * 
 * @author prvoslav
 *
 */
public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -1869548136546750302L;
	private final Object principal;
	private Object credentials;

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the
	 * {@link #isAuthenticated()} will return <code>false</code>.
	 *
	 */
	public FirebaseAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	/**
	 * This constructor should only be used by
	 * <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
	 * implementations that are satisfied with producing a trusted (i.e.
	 * {@link #isAuthenticated()} = <code>true</code>) authentication token.
	 *
	 * @param principal
	 * @param credentials
	 * @param authorities
	 */
	public FirebaseAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}
	// ~ Methods
	// ========================================================================================================

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}
}
