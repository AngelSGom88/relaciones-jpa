package dev.angel.relaciones._1_n_unidirec_b.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "Persona_1toN_UniB")
@Table(name = "persona_1n_unib")
public class Persona_1toN_UniB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    public Persona_1toN_UniB() {
    }

    public Persona_1toN_UniB(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Persona_1toN_UniB{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
