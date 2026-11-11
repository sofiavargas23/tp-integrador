package ar.edu.undec.ar_edu_undec_proyecto.service;
import ar.edu.undec.ar_edu_undec_proyecto.exception.BadRequestException;
import ar.edu.undec.ar_edu_undec_proyecto.exception.ConflictException;
import ar.edu.undec.ar_edu_undec_proyecto.exception.NotFoundException;
import ar.edu.undec.ar_edu_undec_proyecto.model.Cancha;
import ar.edu.undec.ar_edu_undec_proyecto.model.EstadoReserva;
import ar.edu.undec.ar_edu_undec_proyecto.model.Reserva ;
import ar.edu.undec.ar_edu_undec_proyecto.repository.CanchaRepository;
import ar.edu.undec.ar_edu_undec_proyecto.repository.ClienteRepository;
import ar.edu.undec.ar_edu_undec_proyecto.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final CanchaRepository canchaRepository;
    private final ClienteRepository clienteRepository;
    public ReservaService(ReservaRepository reservaRepository, CanchaRepository canchaRepository, ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.canchaRepository = canchaRepository;
        this.clienteRepository = clienteRepository;
    }
    public List <Reserva> listar(){
        return reservaRepository.findAll();
    }
    public Reserva buscarPorId(Long id){
        return reservaRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("No existe la reserva con id " + id));

    }
    public Reserva crear(Reserva reserva) {
        // 1\. Verificar existencia de cancha y cliente
        Cancha cancha = canchaRepository.findById(reserva.getCanchaId())
                .orElseThrow(() -> new NotFoundException("No existe la cancha con id " + reserva.getCanchaId()));
        if (!clienteRepository.existsById(reserva.getClienteId())) {
            throw new NotFoundException("No existe el cliente con id " + reserva.getClienteId());
        }
         // 2\. Calcular fecha de fin y precio total
        LocalDateTime fin = reserva.getInicio().plusHours(reserva.getHoras());
        reserva.setFin(fin);
        reserva.setPrecioTotal(cancha.getPrecioHora().multiply(BigDecimal.valueOf(reserva.getHoras())));
        reserva.setEstado(EstadoReserva.PENDIENTE);
        // 3\. Validar no superposición
        validarSuperposicion(reserva.getCanchaId(), reserva.getInicio(), fin, null);
        return reservaRepository.save(reserva);
    }
    public Reserva cambiarEstado(Long id, String nuevoEstadoStr) {
        if (nuevoEstadoStr == null || nuevoEstadoStr.trim().isEmpty()) {
            throw new BadRequestException("El estado es obligatorio");
        }
        EstadoReserva nuevoEstado;
        try {
            nuevoEstado = EstadoReserva.valueOf(nuevoEstadoStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Estado inválido: " + nuevoEstadoStr);
        }
        Reserva reserva = buscarPorId(id);
        EstadoReserva actual = reserva.getEstado();

        // Máquina de estados: PENDIENTE -&gt; CONFIRMADA | CANCELADA; CONFIRMADA -> FINALIZADA | CANCELADA
        boolean transicionValida = false;
        if (actual == EstadoReserva.PENDIENTE && (nuevoEstado == EstadoReserva.CONFIRMADA || nuevoEstado == EstadoReserva.CANCELADA)) {
            transicionValida = true;
        } else if (actual == EstadoReserva.CONFIRMADA && (nuevoEstado == EstadoReserva.FINALIZADA || nuevoEstado == EstadoReserva.CANCELADA)) {
            transicionValida = true;
        }
        if (!transicionValida) {
            throw new ConflictException("No se puede cambiar el estado de " + actual + " a " + nuevoEstado);
        }
        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }
    public List <Reserva> reservasDeCanchaPorFecha(Long canchaId, LocalDate fecha) {
        if (!canchaRepository.existsById(canchaId)){
            throw new NotFoundException("No existe la cancha con id " + canchaId);

        }
        LocalDateTime desde = fecha.atStartOfDay();
        LocalDateTime hasta = fecha.atTime(23, 59, 59);
        return reservaRepository.findByCanchaIdAndInicioBetween(canchaId, desde, hasta);
    }
    public List<Reserva>reservasDeCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new NotFoundException("No existe el cliente con id " + clienteId);
        }
         return reservaRepository.findByClienteId(clienteId);
    }
    private void validarSuperposicion(Long canchaId, LocalDateTime inicio, LocalDateTime fin, Long reservaIdExcluida) {
        List<Reserva> activas = reservaRepository.findByCanchaIdAndEstadoNot(canchaId, EstadoReserva.CANCELADA);
        for (Reserva r : activas) { if (reservaIdExcluida != null && r.getId().equals(reservaIdExcluida)) {
            continue;
        }
        LocalDateTime rFin = r.getFin() != null ? r.getFin() : r.getInicio().plusHours(r.getHoras());
            // Superposición estricta: nuevaInicio < existenteFin && nuevaFin > existenteInicio
            if (inicio.isBefore(rFin) && fin.isAfter(r.getInicio())) {
                throw new ConflictException("La cancha ya se encuentra reservada en el horario seleccionado");
            }
        }
    }
}
