package rs.pscode.pomodorofire.web.facade.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import rs.pscode.pomodorofire.config.auth.firebase.FirebaseTokenHolder;
import rs.pscode.pomodorofire.service.FirebaseService;
import rs.pscode.pomodorofire.service.UserService;
import rs.pscode.pomodorofire.service.impl.UserServiceImpl;
import rs.pscode.pomodorofire.service.shared.RegisterUserInit;
import rs.pscode.pomodorofire.util.StringUtil;
import rs.pscode.pomodorofire.web.facade.WebFacade;

@Service
public class WebFacadeImpl implements WebFacade {

	@Autowired(required = false)
	private FirebaseService firebaseService;

	@Autowired
	@Qualifier(value = UserServiceImpl.NAME)
	private UserService userService;

	@Transactional
	@Override
	public void registerUser(String firebaseToken) {
		if (StringUtil.isBlank(firebaseToken)) {
			throw new IllegalArgumentException("FirebaseTokenBlank");
		}
		FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
		userService.registerUser(new RegisterUserInit(tokenHolder.getUid(), tokenHolder.getEmail()));
	}
}
