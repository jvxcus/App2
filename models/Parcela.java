package models;

import java.util.ArrayList;
import java.util.List;

public class Parcela {
    private String nombre;
    private double tamaño;
    private String ubicacion;
    private List<Cultivo> cultivos;


    public Parcela(String nombre, double tamaño, String ubicacion) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.ubicacion = ubicacion;
        this.cultivos = new ArrayList<>();

    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getTamaño() { return tamaño; }
    public void setTamaño(double tamaño) { this.tamaño = tamaño; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public List<Cultivo> getCultivos() { return cultivos; }
    public void agregarCultivo(Cultivo cultivo) {
        this.cultivos.add(cultivo);
        this.tamaño += cultivo.getSuperficie();
    }


    public String toString() {
        return "Parcela: " + nombre + ", Tamaño: " + tamaño + " ha, Ubicación: " + ubicacion +
                ", Cultivos: " + cultivos.size();
    }
}
