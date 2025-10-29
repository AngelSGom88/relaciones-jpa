package dev.angel.relaciones._n_m_unidirec;

import dev.angel.relaciones._n_m_unidirec.domain.Persona_Nm_Uni;
import dev.angel.relaciones._n_m_unidirec.domain.Proyecto_Nm_Uni;
import dev.angel.relaciones._n_m_unidirec.repository.Persona_Nm_UniRepository;
import dev.angel.relaciones._n_m_unidirec.repository.Proyecto_Nm_UniRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedNmUni {
    @Bean
    CommandLineRunner seed(Persona_Nm_UniRepository perRepo, Proyecto_Nm_UniRepository prRepo) {
        return args -> {
            if (perRepo.count() > 0 || prRepo.count() > 0) return;

            Proyecto_Nm_Uni pr1 = new Proyecto_Nm_Uni(); pr1.setNombre("Proyecto X");
            Proyecto_Nm_Uni pr2 = new Proyecto_Nm_Uni(); pr2.setNombre("Proyecto Y");
            pr1 = prRepo.save(pr1); pr2 = prRepo.save(pr2);

            Persona_Nm_Uni p1 = new Persona_Nm_Uni(); p1.setNombre("Ana UNI-NM");
            p1.getProyectos().add(pr1); p1.getProyectos().add(pr2);
            Persona_Nm_Uni p2 = new Persona_Nm_Uni(); p2.setNombre("Luis UNI-NM");
            p2.getProyectos().add(pr2);

            perRepo.save(p1); perRepo.save(p2);
        };
    }
}
