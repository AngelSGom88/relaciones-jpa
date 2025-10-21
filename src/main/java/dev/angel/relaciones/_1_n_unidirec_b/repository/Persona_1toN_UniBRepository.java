package dev.angel.relaciones._1_n_unidirec_b.repository;

import dev.angel.relaciones._1_n_unidirec_b.domain.Persona_1toN_UniB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Persona_1toN_UniBRepository extends JpaRepository<Persona_1toN_UniB,Long> {
}
