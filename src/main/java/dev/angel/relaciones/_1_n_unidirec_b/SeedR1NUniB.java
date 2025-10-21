package dev.angel.relaciones._1_n_unidirec_b;

import dev.angel.relaciones._1_n_unidirec_b.domain.Persona_1toN_UniB;
import dev.angel.relaciones._1_n_unidirec_b.domain.Direccion_1toN_UniB;
import dev.angel.relaciones._1_n_unidirec_b.repository.Persona_1toN_UniBRepository;
import dev.angel.relaciones._1_n_unidirec_b.repository.Direccion_1toN_UniBRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
@Configuration
public class SeedR1NUniB {

    @Bean
    CommandLineRunner seedUniB(Persona_1toN_UniBRepository personaRepo,
                               Direccion_1toN_UniBRepository dirRepo) {
        return args -> {

            // 1) Persona
            Persona_1toN_UniB ana = new Persona_1toN_UniB();
            ana.setNombre("Ana UNI-B");
            ana = personaRepo.save(ana);

            // 2) Direcciones (ambas apuntan a Ana). 'etiqueta' opcional.
            Direccion_1toN_UniB casa = new Direccion_1toN_UniB();
            casa.setCalle("Gran Vía 123");
            casa.setCiudad("Madrid");
            casa.setCp("28013");
            casa.setProvincia("Madrid");
            casa.setEtiqueta("Casa");
            casa.setPersona(ana);

            Direccion_1toN_UniB trabajo = new Direccion_1toN_UniB();
            trabajo.setCalle("Paseo de la Castellana 200");
            trabajo.setCiudad("Madrid");
            trabajo.setCp("28046");
            trabajo.setProvincia("Madrid");
            trabajo.setEtiqueta("Trabajo");
            trabajo.setPersona(ana);

            dirRepo.save(casa);
            dirRepo.save(trabajo);
        };
    }

}
