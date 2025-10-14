package dev.angel.relaciones._1_1_unidirec.domain;


import jakarta.persistence.*;

@Entity(name = "PersonaUNI")
@Table(name = "PersonaUNI")
public class PersonaUni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="dni_id")
    private DniUni dni;

    public PersonaUni() {
        super();
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

    public DniUni getDni() {
        return dni;
    }

    public void setDni(DniUni dniUni) {
        this.dni = dniUni;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }
}
