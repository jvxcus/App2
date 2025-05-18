package utils;

import models.Actividad;
import models.Cultivo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class parser {
    public static Cultivo parsearLinea(String linea) {
        try {
            // 1. Localizar el inicio de las actividades (entre corchetes)
            int indiceActividades = linea.indexOf("[");
            String datosCultivo = linea.substring(0, indiceActividades).trim();
            String datosActividades = linea.substring(indiceActividades).trim();

            // 2. Parsear datos del cultivo
            // Evitar el campo "Cultivo" y dividir los demás por coma
            String[] partes = datosCultivo.split(",");
            if (partes.length < 7) {
                System.out.println("Línea mal formada: " + linea);
                return null;
            }

            String nombre = partes[1].trim();         // Pimiento
            String variedad = partes[2].trim();       // Rojo
            double superficie = Double.parseDouble(partes[3].trim()); // 53.8
            String parcela = partes[4].trim();        // PARCELA-I46
            LocalDate fechaSiembra = LocalDate.parse(partes[5].trim()); // 2023-01-14
            String estado = partes[6].trim();         // ACTIVO

            // 3. Parsear actividades
            List<Actividad> actividades = new ArrayList<>();
            datosActividades = datosActividades.replace("[", "").replace("]", "").replace("\"", "");
            String[] actividadesSeparadas = datosActividades.split(",");

            for (String a : actividadesSeparadas) {
                a = a.trim();
                if (a.isEmpty()) continue;
                String[] partesActividad = a.split(":");
                if (partesActividad.length == 2) {
                    String tipo = partesActividad[0].trim();
                    LocalDate fecha = LocalDate.parse(partesActividad[1].trim());
                    actividades.add(new Actividad(tipo, fecha));
                }
            }

            // 4. Crear y retornar Cultivo
            return new Cultivo(nombre, variedad, superficie, parcela, fechaSiembra, estado, actividades);

        } catch (Exception e) {
            System.out.println("Error al parsear línea: " + linea);
            e.printStackTrace();
            return null;
        }
    }
}
