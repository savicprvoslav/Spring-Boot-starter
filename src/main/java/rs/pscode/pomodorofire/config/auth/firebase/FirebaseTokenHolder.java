package rs.pscode.pomodorofire.config.auth.firebase;

import java.util.ArrayList;

import com.google.api.client.util.ArrayMap;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseTokenHolder {
	private FirebaseToken token;

	public FirebaseTokenHolder(FirebaseToken token) {
		this.token = token;
	}

	public String getEmail() {
		return token.getEmail();
	}

	public String getIssuer() {
		return token.getIssuer();
	}

	public String getName() {
		return token.getName();
	}

	public String getUid() {
		return token.getUid();
	}

	public String getGoogleId() {
		String userId = ((ArrayList<String>) ((ArrayMap) ((ArrayMap) token.getClaims().get("firebase"))
				.get("identities")).get("google.com")).get(0);

		return userId;
	}

}
