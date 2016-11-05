package rs.pscode.pomodorofire.web.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.pscode.pomodorofire.config.auth.firebase.FirebaseTokenHolder;
import rs.pscode.pomodorofire.service.FirebaseService;
import rs.pscode.pomodorofire.web.facade.WebFacade;

@RestController
public class SignupResource {

	private static Logger logger = Logger.getLogger(SignupResource.class);

	@Autowired
	private WebFacade facade;

	@RequestMapping(value = "/api/open/firebase/signup", method = RequestMethod.POST)
	public void signUp(@RequestHeader String firebaseToken) {
		logger.info(firebaseToken);
		facade.registerUser(firebaseToken);
	}
}
