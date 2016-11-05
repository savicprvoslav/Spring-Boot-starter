package rs.pscode.pomodorofire.service;

import rs.pscode.pomodorofire.config.auth.firebase.FirebaseTokenHolder;

public interface FirebaseService {

	FirebaseTokenHolder parseToken(String idToken);

}
