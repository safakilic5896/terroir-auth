package fr.epita.terroirauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TerroirAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerroirAuthApplication.class, args);
	}

}
