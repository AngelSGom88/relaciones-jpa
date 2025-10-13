package dev.angel.relaciones._1_1_bidirec.repository;

import dev.angel.relaciones._1_1_bidirec.domain.DniBi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DniBiRepository extends JpaRepository<DniBi,Long> {


    boolean existsByNumero(String numero);
}
