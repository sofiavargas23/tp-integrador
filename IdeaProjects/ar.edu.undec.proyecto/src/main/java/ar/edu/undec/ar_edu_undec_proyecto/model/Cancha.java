package ar.edu.undec.ar_edu_undec_proyecto.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Cancha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El deporte es obligatorio")
    private String deporte;

    @NotNull(message = "El precio por hora es obligatorio")
    @Positive(message = "El precio por hora debe ser positivo")
    private BigDecimal precioHora;

    public Cancha() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getDeporte() {return deporte;}
    public void setDeporte(String deporte) {this.deporte = deporte;}
    public BigDecimal getPrecioHora() {return precioHora;}
    public void setPrecioHora(BigDecimal precioHora) {this.precioHora = precioHora;}
}
