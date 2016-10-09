package rs.pscode.pomodorofire.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import rs.pscode.pomodorofire.domain.dao.DaoPackage;

@EnableJpaRepositories(basePackageClasses = DaoPackage.class)
public class DataConfig {

}
