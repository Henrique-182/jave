package br.com.jave.integrationtests.testcontainers.v1;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
	
	private static String url = "jdbc:postgresql://localhost:5433/jave-test";
	private static String username = "postgres";
	private static String password = "12345678";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");
            
            Startables.deepStart(Stream.of(postgres)).join();

            ConfigurableEnvironment environment = applicationContext.getEnvironment();

			MapPropertySource testcontainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration(postgres));
            
            environment.getPropertySources().addFirst(testcontainers);
        }

        private Map<String, String> createConnectionConfiguration(PostgreSQLContainer<?> postgres) {
            return Map.of(
                    "spring.datasource.url", url,
                    "spring.datasource.username", username,
                    "spring.datasource.password", password
            );
        }

    }
	
}