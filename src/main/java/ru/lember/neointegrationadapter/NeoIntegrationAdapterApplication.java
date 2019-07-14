package ru.lember.neointegrationadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.lember.neointegrationadapter.configuration.projectA.RoutingConfiguration;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.lember.neointegrationadapter.configuration"})
@Import(value = {RoutingConfiguration.class})
public class NeoIntegrationAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeoIntegrationAdapterApplication.class, args);
	}

}
