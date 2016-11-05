package rs.pscode.pomodorofire.service.impl;

import org.springframework.stereotype.Service;

import rs.pscode.pomodorofire.config.auth.firebase.FirebaseTokenHolder;
import rs.pscode.pomodorofire.service.FirebaseService;
import rs.pscode.pomodorofire.service.shared.FirebaseParser;
import rs.pscode.pomodorofire.spring.conditionals.FirebaseCondition;

@Service
@FirebaseCondition
public class FirebaseServiceImpl implements FirebaseService {
	@Override
	public FirebaseTokenHolder parseToken(String firebaseToken) {
		return new FirebaseParser().parseToken(firebaseToken);
	}
}
