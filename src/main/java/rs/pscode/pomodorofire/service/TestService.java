package rs.pscode.pomodorofire.service;

import java.util.Collection;

import rs.pscode.pomodorofire.domain.model.TestEntity;

public interface TestService {

	public String test();

	Collection<TestEntity> testRepository();
	
	TestEntity create();

}
