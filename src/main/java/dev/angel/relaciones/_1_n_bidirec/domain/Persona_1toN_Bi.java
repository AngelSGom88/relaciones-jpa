package dev.angel.relaciones._1_n_bidirec.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Persona_1toN_Bi")
@Table(name = "persona_1n_bi")
public class Persona_1toN_Bi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @JsonManagedReference
    @OneToMany(mappedBy = "persona",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Direccion_1toN_Bi> direcciones = new ArrayList<>();


    public Persona_1toN_Bi() {
    }

    // --- helpers bidireccionales ---
    public void addDireccion(Direccion_1toN_Bi d) {
        if (d == null) return;
        d.setPersona(this);
        this.direcciones.add(d);
    }

    /** Reemplazo total (útil para PUT) */
    public void clearAndSetDirecciones(List<Direccion_1toN_Bi> nuevas) {
        this.direcciones.forEach(d -> d.setPersona(null));
        this.direcciones.clear();
        if (nuevas != null) nuevas.forEach(this::addDireccion);
    }

    public void removeDireccionById(Long direccionId) {
        this.direcciones.removeIf(d -> {
            boolean match = Objects.equals(d.getId(), direccionId);
            if (match) d.setPersona(null);
            return match;
        });
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Direccion_1toN_Bi> getDirecciones() { return direcciones; }
    public void setDirecciones(List<Direccion_1toN_Bi> direcciones) { this.direcciones = direcciones; }

    @Override public boolean equals(Object o){ return o instanceof Persona_1toN_Bi p && Objects.equals(id, p.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }

    @Override
    public String toString() {
        return "Persona_1toN_Bi{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }
}
