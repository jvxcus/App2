package utils;

import models.Cultivo;
import models.Actividad;
import models.EstadoCultivo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Parsear una línea CSV a objeto Cultivo
    public static Cultivo parseCultivo(String linea) {
        try {
            // Quita comillas y corchetes
            String[] partes = linea.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < partes.length; i++) {
                partes[i] = partes[i].replace("\"", "").trim();
            }

            String nombre = partes[1];
            String variedad = partes[2];
            double superficie = Double.parseDouble(partes[3].replace(",", "."));
            String parcela = partes[4];
            LocalDate fechaSiembra = LocalDate.parse(partes[5], FORMATTER);
            EstadoCultivo estado = EstadoCultivo.valueOf(partes[6]);

            List<Actividad> actividades = parseActividades(partes[7]);
            Cultivo cultivo = new Cultivo(nombre, variedad, superficie, parcela, fechaSiembra, estado);
            cultivo.getActividades().addAll(actividades);
            return cultivo;
        } catch (Exception e) {
            System.err.println("Error al parsear cultivo: " + linea);
            throw e;
        }
    }

    // Parsear string de actividades tipo ["RIEGO:2023-01-01","COSECHA:2023-02-01"]
    public static List<Actividad> parseActividades(String texto) {
        List<Actividad> actividades = new ArrayList<>();

        texto = texto.trim();
        if (texto.startsWith("["))
            texto = texto.substring(1);
        if (texto.endsWith("]"))
            texto = texto.substring(0, texto.length() - 1);

        if (texto.isEmpty())
            return actividades;

        String[] elementos = texto.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String elemento : elementos) {
            elemento = elemento.trim().replace("\"", "");
            if (elemento.isEmpty()) continue;

            String[] partes = elemento.split(":");
            if (partes.length == 2) {
                String tipo = partes[0].trim();
                LocalDate fecha = LocalDate.parse(partes[1].trim(), FORMATTER);
                actividades.add(new Actividad(tipo, fecha));
            }
        }
        return actividades;
    }

    // Convertir lista de cultivos a lista de líneas CSV
    public static List<String> generarLineasCSV(List<Cultivo> cultivos) {
        List<String> lineas = new ArrayList<>();
        for (Cultivo c : cultivos) {
            lineas.add(c.toCSV());
        }
        return lineas;
    }
}
