package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Actividad {
    private String tipo; // Ej: "RIEGO", "FERTILIZACION"
    private LocalDate fecha;
    private boolean completada;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Actividad(String tipo, LocalDate fecha) {
        this.tipo = tipo.toUpperCase();
        this.fecha = fecha;
        this.completada = false;
    }

    public Actividad(String tipo, LocalDate fecha, boolean completada) {
        this.tipo = tipo.toUpperCase();
        this.fecha = fecha;
        this.completada = completada;
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo.toUpperCase();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void marcarComoCompletada() {
        this.completada = true;
    }

    // Para mostrar en consola
    @Override
    public String toString() {
        return tipo + " - " + fecha.format(DATE_FORMATTER) + (completada ? " [COMPLETADA]" : "");
    }

    // Para guardar en CSV como string: "RIEGO:2023-03-15"
    public String toCSV() {
        return tipo + ":" + fecha.format(DATE_FORMATTER);
    }

    // Parsear desde CSV individual: "RIEGO:2023-03-15"
    public static Actividad fromCSV(String input) {
        String[] partes = input.split(":");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato de actividad inválido: " + input);
        }
        String tipo = partes[0].trim();
        LocalDate fecha = LocalDate.parse(partes[1].trim(), DATE_FORMATTER);
        return new Actividad(tipo, fecha);
    }

    // Igualdad basada en tipo y fecha
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actividad)) return false;
        Actividad that = (Actividad) o;
        return Objects.equals(tipo, that.tipo) && Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, fecha);
    }
}
