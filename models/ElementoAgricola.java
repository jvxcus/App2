package models;

import java.time.LocalDate;

public abstract class ElementoAgricola {
    protected String nombre;
    protected LocalDate fechaSiembra;
    protected EstadoCultivo estado;

    public ElementoAgricola(String nombre, LocalDate fechaSiembra, EstadoCultivo estado) {
        this.nombre = nombre;
        this.fechaSiembra = fechaSiembra;
        this.estado = estado;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaSiembra() {
        return fechaSiembra;
    }

    public EstadoCultivo getEstado() {
        return estado;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaSiembra(LocalDate fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

    public void setEstado(EstadoCultivo estado) {
        this.estado = estado;
    }

    // Método abstracto para conversión a CSV (obligatorio en subclases)
    public abstract String toCSV();
}
