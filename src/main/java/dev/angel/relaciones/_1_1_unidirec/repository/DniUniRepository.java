package dev.angel.relaciones._1_1_unidirec.repository;

import dev.angel.relaciones._1_1_unidirec.domain.DniUni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DniUniRepository extends JpaRepository<DniUni,Long> {
    boolean existsByNumero(String numero);
}
