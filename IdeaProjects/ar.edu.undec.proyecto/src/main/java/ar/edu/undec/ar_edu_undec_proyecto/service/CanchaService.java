package ar.edu.undec.ar_edu_undec_proyecto.service;
import ar.edu.undec.ar_edu_undec_proyecto.exception.ConflictException;
import ar.edu.undec.ar_edu_undec_proyecto.exception.NotFoundException;
import ar.edu.undec.ar_edu_undec_proyecto.model.Cancha;
import ar.edu.undec.ar_edu_undec_proyecto.repository.CanchaRepository;
import ar.edu.undec.ar_edu_undec_proyecto.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CanchaService {
    private final CanchaRepository canchaRepository;
    private final ReservaRepository reservaRepository;

    public CanchaService(CanchaRepository canchaRepository, ReservaRepository reservaRepository) {
        this.canchaRepository = canchaRepository;
        this.reservaRepository = reservaRepository;
    }

    public List<Cancha> listar() {
        return canchaRepository.findAll();
    }

    public Cancha buscarPorId(Long id) {
        return canchaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la cancha con id " + id));
    }

    public Cancha crear(Cancha cancha) {
        return canchaRepository.save(cancha);
    }

    public Cancha actualizar(Long id, Cancha datos) {
        Cancha cancha = buscarPorId(id);
        cancha.setNombre(datos.getNombre());
        cancha.setDeporte(datos.getDeporte());
        cancha.setPrecioHora(datos.getPrecioHora());
        return canchaRepository.save(cancha);
    }

    public void eliminar(Long id) {
        Cancha cancha = buscarPorId(id);
        if (reservaRepository.existsByCanchaId(id)) {
            throw new ConflictException("No se puede eliminar la cancha porque tiene reservas asociadas");
        }
        canchaRepository.delete(cancha);
    }
}





