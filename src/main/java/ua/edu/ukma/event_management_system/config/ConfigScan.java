package ua.edu.ukma.event_management_system.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("ua.edu.ukma")
@EnableAspectJAutoProxy
@EnableScheduling
public class ConfigScan {
}
