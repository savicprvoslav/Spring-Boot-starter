package rs.pscode.pomodorofire.service.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import rs.pscode.pomodorofire.config.SecurityConfig.Roles;
import rs.pscode.pomodorofire.domain.dao.TestRepository;
import rs.pscode.pomodorofire.domain.model.TestEntity;
import rs.pscode.pomodorofire.service.TestService;
import rs.pscode.pomodorofire.util.StringUtil;

@Service
public class TestServiceImpl implements TestService {

	private final static String COUNTER_TEST = "rs.pscode.entity.test.";

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private CounterService counterService;

	@Transactional
	@Secured(value = Roles.ROLE_USER)
	public Collection<TestEntity> findAll() {
		return testRepository.findAll();
	}

	@Transactional
	@Secured(value = Roles.ROLE_USER)
	public TestEntity create(String name) {
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("TestNameIsBlank");
		}
		//TODO Create event here
		counterService.increment(COUNTER_TEST + "created");

		TestEntity entity = new TestEntity(name);
		return testRepository.save(entity);
	}
}
