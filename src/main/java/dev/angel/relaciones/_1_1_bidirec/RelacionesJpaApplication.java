package dev.angel.relaciones._1_1_bidirec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {
				"dev.angel.relaciones._1_1_bidirec",   // solo BI
				"dev.angel.relaciones.exception"       // handler global
		}
)
public class RelacionesJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelacionesJpaApplication.class, args);
	}

}
