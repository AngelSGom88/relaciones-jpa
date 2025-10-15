package dev.angel.relaciones._1_n_bidirec.repository;

import dev.angel.relaciones._1_n_bidirec.domain.Persona_1toN_Bi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Persona_1toN_BiRepository extends JpaRepository<Persona_1toN_Bi,Long> {
}
