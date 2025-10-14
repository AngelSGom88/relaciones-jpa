package dev.angel.relaciones._1_1_unidirec;


import dev.angel.relaciones._1_1_unidirec.domain.DniUni;
import dev.angel.relaciones._1_1_unidirec.domain.PersonaUni;
import dev.angel.relaciones._1_1_unidirec.repository.PersonaUniRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class SeedR11Uni {

    @Bean
    CommandLineRunner initData(PersonaUniRepository personaRepository) {
        return args -> {

            // 🔹 Persona 1
            PersonaUni ada = new PersonaUni();
            ada.setNombre("Ada");

            DniUni dniAda = new DniUni();
            dniAda.setNumero("12345678A");
            dniAda.setFechaExpedicion(LocalDate.of(2024, 9, 23));

            // Enlazamos ambos lados
            ada.setDni(dniAda); // si tienes los setters sincronizados, con esto basta


            personaRepository.save(ada); // cascade guarda también el DNI


            // 🔹 Persona 2
            PersonaUni alan = new PersonaUni();
            alan.setNombre("Alan");

            DniUni dniAlan = new DniUni();
            dniAlan.setNumero("87654321B");
            dniAlan.setFechaExpedicion(LocalDate.of(2024, 9, 24));

            alan.setDni(dniAlan);


            personaRepository.save(alan);

            // --- 🔍 Búsquedas y prints ---
            System.out.println("\n📋 Todas las personas en BD:");
            personaRepository.findAll().forEach(System.out::println);

            System.out.println("\n🔍 Persona con ID 1:");
            System.out.println(personaRepository.findById(1L).orElse(null));

            System.out.println("\n🔍 Persona cuyo DNI empieza por 123:");
            personaRepository.findAll().stream()
                    .filter(p -> p.getDni() != null && p.getDni().getNumero().startsWith("123"))
                    .forEach(System.out::println);
        };
    }
}
