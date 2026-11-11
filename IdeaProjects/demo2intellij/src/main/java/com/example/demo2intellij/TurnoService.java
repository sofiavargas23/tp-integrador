package com.example.demo2intellij;

public class TurnoService {
    private final TurnoRepository repositorio;

    public TurnoService(){
        this.repositorio = new TurnoRepository();
    }
    public Turno consultarTurno(Long id) {
        return repositorio.buscarPorId(id);
    }
}
