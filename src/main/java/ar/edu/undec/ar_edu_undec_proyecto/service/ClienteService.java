package ar.edu.undec.ar_edu_undec_proyecto.service;

import ar.edu.undec.ar_edu_undec_proyecto.exception.ConflictException;
import ar.edu.undec.ar_edu_undec_proyecto.exception.NotFoundException;
import ar.edu.undec.ar_edu_undec_proyecto.model.Cliente;
import ar.edu.undec.ar_edu_undec_proyecto.repository.ClienteRepository;
import ar.edu.undec.ar_edu_undec_proyecto.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;

    public ClienteService(ClienteRepository clienteRepository, ReservaRepository reservaRepository) {
        this.clienteRepository = clienteRepository;
        this.reservaRepository = reservaRepository;
    }
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }
    public Cliente buscarPorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("No existe el cliente con id "+ id));
    }

    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    public Cliente actualizar(Long id, Cliente datos) {
        Cliente cliente = buscarPorId(id);
        cliente.setNombre(datos.getNombre());
        cliente.setTelefono(datos.getTelefono());
        return clienteRepository.save(cliente);
    }
    public void eliminar(Long id) {
        Cliente cliente = buscarPorId(id);
        if (reservaRepository.existsByClienteId(id)) {
            throw new ConflictException("No se puede eliminar el cliente porque tiene reservas asociadas");
        } clienteRepository.delete(cliente);
    }
}
