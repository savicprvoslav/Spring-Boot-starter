package rs.pscode.pomodorofire.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import rs.pscode.pomodorofire.domain.model.UserEntity;
import rs.pscode.pomodorofire.service.shared.RegisterUserInit;

public interface UserService extends UserDetailsService {

	UserEntity registerUser(RegisterUserInit init);

}
