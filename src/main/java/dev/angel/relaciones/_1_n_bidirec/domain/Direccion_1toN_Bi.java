package dev.angel.relaciones._1_n_bidirec.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity(name = "Direccion_1toN_Bi")
@Table(
        name = "direccion_1n_bi",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_persona_calle_ciudad_cp_prov",
                columnNames = {"persona_id", "calle", "ciudad", "cp", "provincia"}
        )
)
public class Direccion_1toN_Bi {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String etiqueta; // "casa", "oficina", ...
    @NotBlank private String calle;
    @NotBlank private String ciudad;
    private String provincia;
    private String cp;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona_1toN_Bi persona;

    public Direccion_1toN_Bi() { }

    public Direccion_1toN_Bi(String etiqueta, String calle, String ciudad, String provincia, String cp) {
        this.etiqueta = etiqueta;
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.cp = cp;
    }

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }
    public Persona_1toN_Bi getPersona() { return persona; }
    public void setPersona(Persona_1toN_Bi persona) { this.persona = persona; }

    @Override public boolean equals(Object o){ return o instanceof Direccion_1toN_Bi d && Objects.equals(id, d.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}
