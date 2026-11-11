package com.example.demo2intellij;

public class TurnoRepository {
    public Turno buscarPorId(Long id) {
        return new Turno(id, "Sofia Vargas", "10:00");
    }
}
