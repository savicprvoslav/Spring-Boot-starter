package rs.pscode.pomodorofire.web.api;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
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

import rs.pscode.pomodorofire.service.TestService;
import rs.pscode.pomodorofire.web.config.WebConfig;
import rs.pscode.pomodorofire.web.dto.TestJson;

@RestController
public class TestResource {

	private static Logger logger = Logger.getLogger(TestResource.class);

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
		printUser();
		return new HashMap<String, String>();
	}

	@RequestMapping(value = "/api/client/test", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public TestJson apiClientTest() {
		printUser();
		return modelMapper.map(testService.create(), TestJson.class);
	}

	@RequestMapping(value = "/api/admin/test", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<TestJson> apiAdminTest(Principal principal) {
		printUser();
		Type listType = new TypeToken<List<TestJson>>() {
		}.getType();

		return modelMapper.map(testService.testRepository(), listType);
	}

	private void printUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		logger.info(userDetails.getUsername() + " " + Arrays.toString(userDetails.getAuthorities().toArray()));
	}
}
