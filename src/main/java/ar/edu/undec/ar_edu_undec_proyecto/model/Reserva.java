package ar.edu.undec.ar_edu_undec_proyecto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El id de cancha es obligatorio")
    private Long canchaId;
    @NotNull(message = "El id de cliente es obligatorio")
    private Long clienteId;
    @NotNull(message = "La fecha y hora de inicio es obligatoria")
    @Future(message = "La fecha y hora de inicio debe ser futura")
    private LocalDateTime inicio;
    @Min(value = 1, message = "La cantidad de horas debe ser entre 1 y 3")
    @Max(value = 3, message = "La cantidad de horas debe ser entre 1 y 3")
    private int horas;
    @JsonIgnore
    private LocalDateTime fin;
    private BigDecimal precioTotal;
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;
    public Reserva() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCanchaId() { return canchaId; }
    public void setCanchaId(Long canchaId) { this.canchaId = canchaId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public int getHoras() { return horas; }
    public void setHoras(int horas) { this.horas = horas; }
    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
    public BigDecimal getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(BigDecimal precioTotal) { this.precioTotal = precioTotal; }
    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }
}
