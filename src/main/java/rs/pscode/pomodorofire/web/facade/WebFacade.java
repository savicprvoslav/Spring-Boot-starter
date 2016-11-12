package rs.pscode.pomodorofire.web.facade;

import java.util.List;

import rs.pscode.pomodorofire.web.dto.test.TestJson;
import rs.pscode.pomodorofire.web.dto.test.TestRequestJson;

public interface WebFacade {

	void registerUser(String firebaseToken);

	TestJson createTest(TestRequestJson json);

	List<TestJson> getTaskList();

}
