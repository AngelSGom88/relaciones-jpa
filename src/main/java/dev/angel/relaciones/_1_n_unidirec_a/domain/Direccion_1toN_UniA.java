package dev.angel.relaciones._1_n_unidirec_a.domain;

import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(name = "uk_dir_unia", columnNames = {"calle","ciudad","cp","provincia"})
)
public class Direccion_1toN_UniA {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String calle;
    @Column(nullable = false) private String ciudad;
    @Column(nullable = false) private String cp;
    @Column(nullable = false) private String provincia;

    // opcional, no participa en UNIQUE
    private String etiqueta;

    // NO hay referencia a Persona aquí (unidireccional A)

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }
}
