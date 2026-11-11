package ar.edu.undec.ar_edu_undec_proyecto.controller;

import ar.edu.undec.ar_edu_undec_proyecto.model.Cancha;
import ar.edu.undec.ar_edu_undec_proyecto.model.Reserva;
import ar.edu.undec.ar_edu_undec_proyecto.service.CanchaService;
import ar.edu.undec.ar_edu_undec_proyecto.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/canchas")
public class CanchaController {
    private final CanchaService canchaService;
    private final ReservaService reservaService;

    public CanchaController(CanchaService canchaService, ReservaService reservaService) {
        this.canchaService = canchaService;
        this.reservaService = reservaService;
    }
    @GetMapping
    public List<Cancha>listar(){
        return canchaService.listar();
    }
    @GetMapping("/{id}")
    public Cancha obtener(@PathVariable Long id) {
        return canchaService.buscarPorId(id);
    }
    @PostMapping
    public ResponseEntity<Cancha> crear(@Valid @RequestBody Cancha cancha) {
        Cancha creada = canchaService.crear(cancha);
        return ResponseEntity.created(URI.create("/api/canchas/" + creada.getId())).body(creada);
    }
    @PutMapping("/{id}")
    public Cancha actualizar(@PathVariable Long id, @Valid @RequestBody Cancha cancha) {
        return canchaService.actualizar(id, cancha);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        canchaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/reservas")
    public List<Reserva> reservasPorFecha(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return reservaService.reservasDeCanchaPorFecha(id, fecha);
    }
}





