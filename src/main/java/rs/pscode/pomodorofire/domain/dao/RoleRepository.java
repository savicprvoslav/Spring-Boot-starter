package rs.pscode.pomodorofire.domain.dao;

import org.springframework.data.repository.CrudRepository;

import rs.pscode.pomodorofire.domain.model.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

	RoleEntity findByAuthority(String authority);
}
