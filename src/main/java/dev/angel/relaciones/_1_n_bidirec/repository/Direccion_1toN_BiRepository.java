package dev.angel.relaciones._1_n_bidirec.repository;

import dev.angel.relaciones._1_n_bidirec.domain.Direccion_1toN_Bi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Direccion_1toN_BiRepository extends JpaRepository<Direccion_1toN_Bi,Long> {
    boolean existsByCalleAndCiudadAndCpAndProvincia(String calle, String ciudad, String cp, String provincia);
}
