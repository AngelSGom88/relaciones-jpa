package dev.angel.relaciones._1_1_unidirec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {
				"dev.angel.relaciones._1_1_unidirec",   // solo uni
				"dev.angel.relaciones.exception"       // handler global
		}
)
public class RelacionesJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelacionesJpaApplication.class, args);
	}

}
