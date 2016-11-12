package rs.pscode.pomodorofire.service;

import java.util.Collection;

import rs.pscode.pomodorofire.domain.model.TestEntity;

public interface TestService {

	Collection<TestEntity> findAll();

	TestEntity create(String name);

}
