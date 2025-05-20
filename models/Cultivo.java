package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cultivo extends ElementoAgricola {
    private String variedad;
    private double superficie; // en hectáreas
    private String parcela;    // código de parcela
    private List<Actividad> actividades;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Cultivo(String nombre, String variedad, double superficie, String parcela,
                   LocalDate fechaSiembra, EstadoCultivo estado) {
        super(nombre, fechaSiembra, estado);
        this.variedad = variedad;
        this.superficie = superficie;
        this.parcela = parcela;
        this.actividades = new ArrayList<>();
    }

    // Getters
    public String getVariedad() {
        return variedad;
    }

    public double getSuperficie() {
        return superficie;
    }

    public String getParcela() {
        return parcela;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    // Setters
    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    // Métodos para actividades
    public void agregarActividad(Actividad act) {
        actividades.add(act);
    }

    public void eliminarActividad(Actividad act) {
        actividades.remove(act);
    }

    public boolean tieneActividadesPendientes() {
        return actividades.stream().anyMatch(a -> !a.isCompletada());
    }

    // Conversión a CSV
    @Override
    public String toCSV() {
        StringBuilder actividadesStr = new StringBuilder("[");
        for (int i = 0; i < actividades.size(); i++) {
            actividadesStr.append("\"").append(actividades.get(i).toCSV()).append("\"");
            if (i < actividades.size() - 1) {
                actividadesStr.append(",");
            }
        }
        actividadesStr.append("]");

        return String.format("Cultivo,\"%s\",\"%s\",%.2f,\"%s\",\"%s\",\"%s\",%s",
                nombre, variedad, superficie, parcela,
                fechaSiembra.format(FORMATTER),
                estado.name(),
                actividadesStr.toString());
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %.1f ha - Parcela: %s - Estado: %s",
                nombre, variedad, superficie, parcela, estado);
    }

    // Creador estático desde CSV (requiere lista ya parseada de actividades)
    public static Cultivo fromCSV(String[] campos, List<Actividad> actividades) {
        String nombre = campos[1];
        String variedad = campos[2];
        double superficie = Double.parseDouble(campos[3]);
        String parcela = campos[4];
        LocalDate fechaSiembra = LocalDate.parse(campos[5], FORMATTER);
        EstadoCultivo estado = EstadoCultivo.valueOf(campos[6]);
        Cultivo cultivo = new Cultivo(nombre, variedad, superficie, parcela, fechaSiembra, estado);
        cultivo.getActividades().addAll(actividades);
        return cultivo;
    }
}
