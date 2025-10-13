package dev.angel.relaciones._1_1_BIDIREC;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity(name = "DniBI")
@Table(name = "DniBI")
public class DniBI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String numero;
    @Column(nullable = false)
    private LocalDate fechaExpedicion;
    @OneToOne
    @JoinColumn(name = "persona_id", unique = true, nullable = false)
    @com.fasterxml.jackson.annotation.JsonBackReference
    private PersonaBI personaBI;

    public DniBI() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public PersonaBI getPersonaBI() {
        return personaBI;
    }

    public void setPersonaBI(PersonaBI persona) {
        this.personaBI = persona;
        // Evita recursión infinita y mantiene ambos lados sincronizados
        if (persona != null && persona.getDni() != this) {
            persona.setDni(this);
        }
    }

    @Override
    public String toString() {
        return "DniBI{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", fechaExpedicion=" + fechaExpedicion +
                '}';
    }
}
