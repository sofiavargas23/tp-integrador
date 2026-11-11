package ar.edu.undec.ar_edu_undec_proyecto.controller;

import ar.edu.undec.ar_edu_undec_proyecto.model.Cliente;
import ar.edu.undec.ar_edu_undec_proyecto.model.Reserva;
import ar.edu.undec.ar_edu_undec_proyecto.service.ClienteService;
import ar.edu.undec.ar_edu_undec_proyecto.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final ReservaService reservaService;
    public ClienteController(ClienteService clienteService, ReservaService reservaService) {
        this.clienteService = clienteService;
        this.reservaService = reservaService;
    }
    @GetMapping
    public List<Cliente> listar(){
        return clienteService.listar();
    }
    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        Cliente creado = clienteService.crear(cliente);
        return ResponseEntity.created(URI.create("/api/clientes/" + creado.getId())).body(creado);
    }
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        return clienteService.actualizar(id, cliente);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reservas")
    public List<Reserva> reservasDeCliente(@PathVariable Long id) {
        return reservaService.reservasDeCliente(id);
    }
}
