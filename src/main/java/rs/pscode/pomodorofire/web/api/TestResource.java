package rs.pscode.pomodorofire.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rs.pscode.pomodorofire.web.dto.test.TestJson;
import rs.pscode.pomodorofire.web.dto.test.TestRequestJson;
import rs.pscode.pomodorofire.web.facade.WebFacade;

@RestController
public class TestResource {

	@Autowired
	private WebFacade webFacade;

	@Value("${env}")
	private String environment;

	@RequestMapping(value = "/api/client/test", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public TestJson apiTestCreate(@RequestBody TestRequestJson request) {
		return webFacade.createTest(request);
	}

	@RequestMapping(value = "/api/client/test", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<TestJson> apiGetTests() {
		return webFacade.getTaskList();
	}

}
