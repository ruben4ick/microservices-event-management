package ua.edu.ukma.event_management_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.edu.ukma.event_management_system.service.DatabasePopulatorService;

@SpringBootApplication
public class EventManagementSystemApplication implements CommandLineRunner {

	private final DatabasePopulatorService databasePopulatorService;
	private static final Logger log = LoggerFactory.getLogger(EventManagementSystemApplication.class);


	public EventManagementSystemApplication(DatabasePopulatorService databasePopulatorService) {
		this.databasePopulatorService = databasePopulatorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(EventManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		databasePopulatorService.populateDatabase();
		log.info("Database populated with test data successfully!");
	}
}
