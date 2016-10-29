package rs.pscode.pomodorofire.web.api;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rs.pscode.pomodorofire.domain.model.TestEntity;
import rs.pscode.pomodorofire.service.TestService;
import rs.pscode.pomodorofire.service.exception.TestException;
import rs.pscode.pomodorofire.web.config.WebConfig;
import rs.pscode.pomodorofire.web.dto.TestJson;

@RestController
public class TestResource {

	@Autowired
	private TestService testService;

	@Autowired
	@Qualifier(WebConfig.MODEL_MAPPER)
	private ModelMapper modelMapper;

	@Value("${env}")
	private String environment;

	@RequestMapping(value = "/api/open/test", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Object apiOpenTest() {
		return new HashMap<String, String>();
	}

	@RequestMapping(value = "/api/client/test", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public TestJson apiClientTest() {
		return modelMapper.map(testService.create(), TestJson.class);
	}

	@RequestMapping(value = "/api/admin/test", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<TestJson> apiAdminTest(Principal principal) {
		// UserDetails userDetails = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Type listType = new TypeToken<List<TestJson>>() {
		}.getType();

		return modelMapper.map(testService.testRepository(), listType);
	}
}
