package rs.pscode.pomodorofire.web.api;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleResource {
	@RequestMapping(value = "/api/open/example", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public Object apiOpen() {
		return new HashMap<String, String>();
	}

	@RequestMapping(value = "/api/client/example", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public Object apiClient() {
		return new HashMap<String, String>();
	}

	@RequestMapping(value = "/api/admin/example", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public Object apiAdmin() {
		return new HashMap<String, String>();
	}
}
