package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parcela {
    private String codigo; // Ej: PARCELA-A01
    private double tamaño; // en hectáreas
    private String ubicacion;
    private List<Cultivo> cultivos;

    public Parcela(String codigo, double tamaño, String ubicacion) {
        this.codigo = codigo;
        this.tamaño = tamaño;
        this.ubicacion = ubicacion;
        this.cultivos = new ArrayList<>();
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getTamaño() {
        return tamaño;
    }

    public void setTamaño(double tamaño) {
        this.tamaño = tamaño;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Cultivo> getCultivos() {
        return cultivos;
    }

    // Métodos de gestión de cultivos
    public void agregarCultivo(Cultivo cultivo) {
        cultivos.add(cultivo);
    }

    public void removerCultivo(Cultivo cultivo) {
        cultivos.remove(cultivo);
    }

    public boolean tieneCultivosActivos() {
        return cultivos.stream().anyMatch(c -> c.getEstado() == EstadoCultivo.ACTIVO);
    }

    @Override
    public String toString() {
        return String.format("Parcela %s - %.2f ha - Ubicación: %s - Cultivos: %d",
                codigo, tamaño, ubicacion, cultivos.size());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Parcela)) return false;
        Parcela other = (Parcela) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
