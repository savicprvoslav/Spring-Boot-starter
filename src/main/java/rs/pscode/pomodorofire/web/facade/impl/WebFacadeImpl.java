package rs.pscode.pomodorofire.web.facade.impl;

import java.lang.reflect.Type;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import rs.pscode.pomodorofire.config.SecurityConfig.Roles;
import rs.pscode.pomodorofire.config.auth.firebase.FirebaseTokenHolder;
import rs.pscode.pomodorofire.domain.model.TestEntity;
import rs.pscode.pomodorofire.service.FirebaseService;
import rs.pscode.pomodorofire.service.TestService;
import rs.pscode.pomodorofire.service.UserService;
import rs.pscode.pomodorofire.service.impl.UserServiceImpl;
import rs.pscode.pomodorofire.service.shared.RegisterUserInit;
import rs.pscode.pomodorofire.util.StringUtil;
import rs.pscode.pomodorofire.web.config.WebConfig;
import rs.pscode.pomodorofire.web.dto.test.TestJson;
import rs.pscode.pomodorofire.web.dto.test.TestRequestJson;
import rs.pscode.pomodorofire.web.facade.WebFacade;

@Service
public class WebFacadeImpl implements WebFacade {

	@Autowired(required = false)
	private FirebaseService firebaseService;

	@Autowired
	private TestService testService;

	@Autowired
	@Qualifier(value = UserServiceImpl.NAME)
	private UserService userService;

	@Autowired
	@Qualifier(WebConfig.MODEL_MAPPER)
	private ModelMapper modelMapper;

	@Transactional
	@Override
	public void registerUser(String firebaseToken) {
		if (StringUtil.isBlank(firebaseToken)) {
			throw new IllegalArgumentException("FirebaseTokenBlank");
		}
		FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
		userService.registerUser(new RegisterUserInit(tokenHolder.getUid(), tokenHolder.getEmail()));
	}

	@Transactional
	@Override
	@Secured(value = Roles.ROLE_USER)
	public TestJson createTest(TestRequestJson json) {
		TestEntity testEntity = testService.create(json.getName());
		return modelMapper.map(testEntity, TestJson.class);
	}

	@Transactional
	@Override
	@Secured(value = Roles.ROLE_USER)
	public List<TestJson> getTaskList() {
		Type listType = new TypeToken<List<TestJson>>() {
		}.getType();

		return modelMapper.map(testService.findAll(), listType);
	}
}
