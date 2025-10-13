package dev.angel.relaciones._1_1_bidirec.domain;

import jakarta.persistence.*;

@Entity(name = "PersonaBI")
@Table(name = "PersonaBI")
public class PersonaBi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;

    @OneToOne(mappedBy = "personaBI", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private DniBi dniBI;

    public PersonaBi() {
        super();
    }

    public DniBi getDni() {
        return dniBI;
    }

    public void setDni(DniBi dni) {
        this.dniBI = dni;
        // Evita recursión infinita y mantiene ambos lados sincronizados
        if (dni != null && dni.getPersonaBI() != this) {
            dni.setPersonaBI(this);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
