package ar.edu.undec.ar_edu_undec_proyecto.repository;

import ar.edu.undec.ar_edu_undec_proyecto.model.EstadoReserva;
import ar.edu.undec.ar_edu_undec_proyecto.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCanchaIdAndInicioBetween (Long canchaId, LocalDateTime desde, LocalDateTime hasta);
    List <Reserva> findByClienteId(Long clienteId);
    List<Reserva> findByCanchaIdAndEstadoNot(Long canchaId, EstadoReserva estadoExcluido);

    boolean existsByCanchaId(Long canchaId);
    boolean existsByClienteId(Long clienteId);

}
