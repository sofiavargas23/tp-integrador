package ar.edu.undec.ar_edu_undec_proyecto.controller;
import ar.edu.undec.ar_edu_undec_proyecto.model.Reserva;
import ar.edu.undec.ar_edu_undec_proyecto.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService reservaService;
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }
    @GetMapping
    public List<Reserva> listar(){
        return reservaService.listar();
    }
    @GetMapping("/{id}")
    public Reserva obtener(@PathVariable Long id) {
        return reservaService.buscarPorId(id);
    }
    @PostMapping
    public ResponseEntity<Reserva> crear(@Valid @RequestBody Reserva reserva) {
        Reserva creada = reservaService.crear(reserva);
        return ResponseEntity.created(URI.create("/api/reservas/" + creada.getId())).body(creada);
    }
    @PatchMapping("/{id}/estado")
    public Reserva cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body){
        String nuevoEstado = body != null ? body.get("estado"): null;
        return reservaService.cambiarEstado(id, nuevoEstado);
    }


}
