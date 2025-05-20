package services;

import models.Cultivo;
import models.EstadoCultivo;
import models.Parcela;
import models.Actividad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestorCultivos {
    private List<Cultivo> cultivos;

    public GestorCultivos() {
        this.cultivos = new ArrayList<>();
    }

    public void setCultivos(List<Cultivo> cultivos) {
        this.cultivos = cultivos;
    }

    public List<Cultivo> getCultivos() {
        return cultivos;
    }

    // Crear nuevo cultivo
    public void crearCultivo(String nombre, String variedad, double superficie, String codigoParcela,
                             LocalDate fechaSiembra, EstadoCultivo estado) {
        Cultivo cultivo = new Cultivo(nombre, variedad, superficie, codigoParcela, fechaSiembra, estado);
        cultivos.add(cultivo);
    }

    // Buscar por nombre o variedad (contiene, ignore case)
    public List<Cultivo> buscarCultivos(String termino) {
        return cultivos.stream()
                .filter(c -> c.getNombre().toLowerCase().contains(termino.toLowerCase())
                        || c.getVariedad().toLowerCase().contains(termino.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Editar cultivo por referencia
    public void editarCultivo(Cultivo cultivo, String nuevoNombre, String nuevaVariedad,
                              double nuevaSuperficie, String nuevaParcela, EstadoCultivo nuevoEstado) {
        cultivo.setNombre(nuevoNombre);
        cultivo.setVariedad(nuevaVariedad);
        cultivo.setSuperficie(nuevaSuperficie);
        cultivo.setParcela(nuevaParcela);
        cultivo.setEstado(nuevoEstado);
    }

    // Eliminar cultivo (solo si no tiene actividades pendientes)
    public boolean eliminarCultivo(Cultivo cultivo) {
        if (cultivo.tieneActividadesPendientes()) {
            return false;
        }
        return cultivos.remove(cultivo);
    }

    // Obtener cultivos por estado
    public List<Cultivo> listarPorEstado(EstadoCultivo estado) {
        return cultivos.stream()
                .filter(c -> c.getEstado() == estado)
                .collect(Collectors.toList());
    }

    // Eliminar todos los cultivos de una parcela específica (usado al eliminar una parcela)
    public void eliminarCultivosDeParcela(String codigoParcela) {
        Iterator<Cultivo> it = cultivos.iterator();
        while (it.hasNext()) {
            Cultivo cultivo = it.next();
            if (cultivo.getParcela().equalsIgnoreCase(codigoParcela)) {
                if (!cultivo.tieneActividadesPendientes()) {
                    it.remove();
                }
            }
        }
    }

    // Obtener todos los cultivos de una parcela específica
    public List<Cultivo> obtenerCultivosDeParcela(String codigoParcela) {
        return cultivos.stream()
                .filter(c -> c.getParcela().equalsIgnoreCase(codigoParcela))
                .collect(Collectors.toList());
    }

    // Exportar todos los cultivos a formato CSV
    public List<String> exportarCultivosComoCSV() {
        return cultivos.stream()
                .map(Cultivo::toCSV)
                .collect(Collectors.toList());
    }

    // Obtener cultivo por índice (para el menú)
    public Cultivo obtenerCultivoPorIndice(int indice) {
        if (indice >= 0 && indice < cultivos.size()) {
            return cultivos.get(indice);
        }
        return null;
    }

    // Mostrar todos los cultivos numerados (para el menú)
    public void mostrarCultivosEnumerados() {
        for (int i = 0; i < cultivos.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, cultivos.get(i));
        }
    }
}
