package dev.angel.relaciones._n_m_bidirec;

import dev.angel.relaciones._n_m_bidirec.domain.Persona_Nm_Bi;
import dev.angel.relaciones._n_m_bidirec.domain.Proyecto_Nm_Bi;
import dev.angel.relaciones._n_m_bidirec.repository.Persona_Nm_BiRepository;
import dev.angel.relaciones._n_m_bidirec.repository.Proyecto_Nm_BiRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedRNmBi {

    @Bean
    CommandLineRunner seed(Persona_Nm_BiRepository perRepo, Proyecto_Nm_BiRepository prRepo){
        return args -> {
            if (perRepo.count()>0 || prRepo.count()>0) return;

            Proyecto_Nm_Bi pr1 = new Proyecto_Nm_Bi(); pr1.setNombre("Proyecto A");
            Proyecto_Nm_Bi pr2 = new Proyecto_Nm_Bi(); pr2.setNombre("Proyecto B");
            pr1 = prRepo.save(pr1); pr2 = prRepo.save(pr2);

            Persona_Nm_Bi p1 = new Persona_Nm_Bi(); p1.setNombre("Ana N-M");
            Persona_Nm_Bi p2 = new Persona_Nm_Bi(); p2.setNombre("Luis N-M");

            p1.getProyectos().add(pr1); pr1.getPersonas().add(p1);
            p1.getProyectos().add(pr2); pr2.getPersonas().add(p1);
            p2.getProyectos().add(pr2); pr2.getPersonas().add(p2);

            perRepo.save(p1); perRepo.save(p2);
        };
    }
}
