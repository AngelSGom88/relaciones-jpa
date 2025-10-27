package dev.angel.relaciones._1_n_unidirec_a.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Persona_1toN_UniA {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Unidireccional: la FK persona_id vive en la tabla de Direccion, pero
    // NO hay campo "persona" en Direccion.
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    @JoinColumn(name = "persona_id", nullable = false) // crea FK en Direccion_1toN_UniA
    private List<Direccion_1toN_UniA> direcciones = new ArrayList<>();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Direccion_1toN_UniA> getDirecciones() { return direcciones; }
    public void setDirecciones(List<Direccion_1toN_UniA> direcciones) { this.direcciones = direcciones; }
}
