package rs.pscode.pomodorofire.domain.dao;

import org.springframework.data.repository.CrudRepository;

import rs.pscode.pomodorofire.domain.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

}
