package de.shippie.sunnybridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SunnybridgeApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(SunnybridgeApplication.class, args);
	}
}
