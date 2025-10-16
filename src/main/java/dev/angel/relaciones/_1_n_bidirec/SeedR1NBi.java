package dev.angel.relaciones._1_n_bidirec;

import dev.angel.relaciones._1_n_bidirec.domain.Direccion_1toN_Bi;
import dev.angel.relaciones._1_n_bidirec.domain.Persona_1toN_Bi;
import dev.angel.relaciones._1_n_bidirec.repository.Persona_1toN_BiRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SeedR1NBi {

    private final Persona_1toN_BiRepository personaRepo;

    public SeedR1NBi(Persona_1toN_BiRepository personaRepo) {
        this.personaRepo = personaRepo;
    }

    @PostConstruct
    public void init() {
        if (personaRepo.count() > 0) return;

        // Persona 1: Ana
        Persona_1toN_Bi ana = new Persona_1toN_Bi();
        ana.setNombre("Ana");
        ana.addDireccion(new Direccion_1toN_Bi("Calle Mayor 1", "Madrid", "28001", "Madrid"));
        ana.addDireccion(new Direccion_1toN_Bi("Gran Vía 10", "Madrid", "28013", "Madrid"));

        // Persona 2: Luis (direcciones distintas a las de Ana para respetar la UNIQUE global)
        Persona_1toN_Bi luis = new Persona_1toN_Bi();
        luis.setNombre("Luis");
        luis.addDireccion(new Direccion_1toN_Bi("Prado 5", "Madrid", "28014", "Madrid"));
        luis.addDireccion(new Direccion_1toN_Bi("Av. Constitución 12", "Sevilla", "41001", "Sevilla"));

        personaRepo.save(ana);
        personaRepo.save(luis);
    }
}
