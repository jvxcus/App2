package models;
import java.time.LocalDate;
import java.util.List;
public class Cultivo {
    private String nombre;
    private String variedad;
    private double superficie;
    private String parcela;
    private LocalDate fechaSiembra;
    private String estado;
    private List<Actividad> actividades;

    public Cultivo(String nombre, String variedad, double superficie, String parcela,
                   LocalDate fechaSiembra, String estado, List<Actividad> actividades) {
        this.nombre = nombre;
        this.variedad = variedad;
        this.superficie = superficie;
        this.actividades = actividades;
        this.estado = estado;
        this.fechaSiembra = fechaSiembra;
        this.parcela = parcela;
    }
    public String toString(){
        return "Cultivo: " + nombre + " (" + variedad + "), " + superficie + " ha, Parcela: " + parcela +
                ", Estado: " + estado + ", Fecha siembra: " + fechaSiembra;
    }
    public String getNombre() { return nombre; }
    public String getVariedad() { return variedad; }
    public double getSuperficie() { return superficie; }
    public String getParcela() { return parcela; }
    public LocalDate getFechaSiembra() { return fechaSiembra; }
    public String getEstado() { return estado; }
    public List<Actividad> getActividades() { return actividades; }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public void setFechaSiembra(LocalDate fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

}

