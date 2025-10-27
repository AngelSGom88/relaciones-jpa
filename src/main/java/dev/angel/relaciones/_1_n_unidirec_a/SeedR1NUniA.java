package dev.angel.relaciones._1_n_unidirec_a;

import dev.angel.relaciones._1_n_unidirec_a.domain.Persona_1toN_UniA;
import dev.angel.relaciones._1_n_unidirec_a.domain.Direccion_1toN_UniA;
import dev.angel.relaciones._1_n_unidirec_a.repository.Persona_1toN_UniARepository;
import dev.angel.relaciones._1_n_unidirec_a.repository.Direccion_1toN_UniARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

@Configuration
public class SeedR1NUniA {

    @Bean
    CommandLineRunner seedUniA(Persona_1toN_UniARepository personaRepo,
                               Direccion_1toN_UniARepository dirRepo) {
        return args -> {
            try {
                // 1) Persona
                Persona_1toN_UniA ana = new Persona_1toN_UniA();
                ana.setNombre("Ana UNI-A");

                // 2) Direcciones (se añaden a la lista del padre; en UNI-A la FK se gestiona con @JoinColumn en Persona)
                Direccion_1toN_UniA casa = new Direccion_1toN_UniA();
                casa.setCalle("Gran Vía 123");
                casa.setCiudad("Madrid");
                casa.setCp("28013");
                casa.setProvincia("Madrid");
                casa.setEtiqueta("Casa");

                Direccion_1toN_UniA trabajo = new Direccion_1toN_UniA();
                trabajo.setCalle("Paseo de la Castellana 200");
                trabajo.setCiudad("Madrid");
                trabajo.setCp("28046");
                trabajo.setProvincia("Madrid");
                trabajo.setEtiqueta("Trabajo");

                ana.getDirecciones().add(casa);
                ana.getDirecciones().add(trabajo);

                // 3) Guardar (cascade PERSIST en @OneToMany de Persona)
                personaRepo.save(ana);

            } catch (DataIntegrityViolationException ignored) {
                // Si ya se ejecutó y existe por UNIQUE(calle,ciudad,cp,provincia), lo ignoramos en dev
            }
        };
    }
}
