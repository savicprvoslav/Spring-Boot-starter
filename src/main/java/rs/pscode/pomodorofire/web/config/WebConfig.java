package rs.pscode.pomodorofire.web.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rs.pscode.pomodorofire.domain.model.TestEntity;
import rs.pscode.pomodorofire.web.dto.test.TestJson;

@Configuration
public class WebConfig {
	public final static String MODEL_MAPPER = "ModelMapperWeb";

	@Bean(name = MODEL_MAPPER)
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(new Converter<TestEntity, TestJson>() {

			public TestJson convert(MappingContext<TestEntity, TestJson> context) {
				TestEntity entity = context.getSource();
				TestJson testJson = context.getDestination();
				testJson.setOutId(entity.getId());
				testJson.setName(entity.getName());

				return testJson;
			}
		});

		return mapper;
	}
}
