package dev.angel.relaciones._1_n_bidirec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {
				"dev.angel.relaciones._1_N_bidirec",   // solo bi
				"dev.angel.relaciones.exception"       // handler global
		}
)
public class RelacionesJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelacionesJpaApplication.class, args);
	}

}
