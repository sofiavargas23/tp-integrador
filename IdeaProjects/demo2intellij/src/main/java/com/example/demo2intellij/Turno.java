package com.example.demo2intellij;

public class Turno {
    private final Long id;
    private final String nombre;
    private final String horario;

    public Turno(Long id, String nombre, String horario) {
        this.id = id;
        this.nombre = nombre;
        this.horario = horario;
    }
    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getHorario(){
        return horario;
    }
}
