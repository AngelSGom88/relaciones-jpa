package dev.angel.relaciones._1_1_UNIDIREC;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class SeedR11Uni {

    @Bean
    CommandLineRunner initData(PersonaUNIRepository personaRepository) {
        return args -> {

            // 🔹 Persona 1
            PersonaUNI ada = new PersonaUNI();
            ada.setNombre("Ada");

            DniUNI dniAda = new DniUNI();
            dniAda.setNumero("12345678A");
            dniAda.setFechaExpedicion(LocalDate.of(2024, 9, 23));

            // Enlazamos ambos lados
            ada.setDniUNI(dniAda); // si tienes los setters sincronizados, con esto basta


            personaRepository.save(ada); // cascade guarda también el DNI


            // 🔹 Persona 2
            PersonaUNI alan = new PersonaUNI();
            alan.setNombre("Alan");

            DniUNI dniAlan = new DniUNI();
            dniAlan.setNumero("87654321B");
            dniAlan.setFechaExpedicion(LocalDate.of(2024, 9, 24));

            alan.setDniUNI(dniAlan);


            personaRepository.save(alan);

            // --- 🔍 Búsquedas y prints ---
            System.out.println("\n📋 Todas las personas en BD:");
            personaRepository.findAll().forEach(System.out::println);

            System.out.println("\n🔍 Persona con ID 1:");
            System.out.println(personaRepository.findById(1L).orElse(null));

            System.out.println("\n🔍 Persona cuyo DNI empieza por 123:");
            personaRepository.findAll().stream()
                    .filter(p -> p.getDniUNI() != null && p.getDniUNI().getNumero().startsWith("123"))
                    .forEach(System.out::println);
        };
    }
}
