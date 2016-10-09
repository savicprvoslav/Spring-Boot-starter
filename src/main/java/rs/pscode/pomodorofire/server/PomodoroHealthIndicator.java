package rs.pscode.pomodorofire.server;

import java.util.Random;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PomodoroHealthIndicator implements HealthIndicator {

	public Health health() {

		int errorCode = check(); // perform some specific health check
		if (errorCode > 8) {
			return Health.down().withDetail("Error Code", errorCode).build();
		}
		return Health.up().build();
	}

	private int check() {
		return new Random().nextInt(10);
	}

}
