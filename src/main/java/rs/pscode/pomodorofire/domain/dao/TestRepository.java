package rs.pscode.pomodorofire.domain.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import rs.pscode.pomodorofire.domain.model.TestEntity;

public interface TestRepository extends CrudRepository<TestEntity, Long> {
	Collection<TestEntity> findAll();
}
