package dev.angel.relaciones._1_1_bidirec.domain;

import jakarta.persistence.*;

@Entity(name = "PersonaBi")
@Table(name = "PersonaBi")
public class PersonaBi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private DniBi dni;

    public PersonaBi() {
        super();
    }

    public DniBi getDni() {
        return dni;
    }

    public void setDni(DniBi dni) {
        this.dni = dni;
        // Evita recursión infinita y mantiene ambos lados sincronizados
        if (dni != null && dni.getPersona() != this) {
            dni.setPersona(this);
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
