package rs.pscode.pomodorofire.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import rs.pscode.pomodorofire.config.SecurityConfig.Roles;
import rs.pscode.pomodorofire.domain.dao.TestRepository;
import rs.pscode.pomodorofire.domain.model.TestEntity;
import rs.pscode.pomodorofire.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private CounterService counterService;

	public String test() {
		return "Test";
	}

	@Transactional
	@Secured(value = Roles.ROLE_ADMIN)
	public Collection<TestEntity> testRepository() {
		testRepository.deleteAll();
		List<TestEntity> entities = new ArrayList<TestEntity>();
		entities.add(new TestEntity());
		entities.add(new TestEntity());
		entities.add(new TestEntity());
		testRepository.save(entities);
		return testRepository.findAll();
	}

	@Transactional
	@Secured(value = Roles.ROLE_USER)
	public TestEntity create() {
		counterService.increment("rs.pscode.pomodoro.created");

		TestEntity entity = new TestEntity();
		return testRepository.save(entity);
	}

}
