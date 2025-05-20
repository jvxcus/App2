package services;

import models.Cultivo;
import models.Actividad;

import java.time.LocalDate;
import java.util.List;

public class GestorActividades {

    // Registrar una nueva actividad a un cultivo
    public void registrarActividad(Cultivo cultivo, String tipo, LocalDate fecha) {
        Actividad nueva = new Actividad(tipo, fecha);
        cultivo.agregarActividad(nueva);
    }

    // Listar todas las actividades de un cultivo
    public List<Actividad> obtenerActividades(Cultivo cultivo) {
        return cultivo.getActividades();
    }

    // Eliminar una actividad por índice (valida rango)
    public boolean eliminarActividad(Cultivo cultivo, int index) {
        List<Actividad> actividades = cultivo.getActividades();
        if (index >= 0 && index < actividades.size()) {
            actividades.remove(index);
            return true;
        }
        return false;
    }

    // Marcar actividad como completada por índice
    public boolean completarActividad(Cultivo cultivo, int index) {
        List<Actividad> actividades = cultivo.getActividades();
        if (index >= 0 && index < actividades.size()) {
            actividades.get(index).marcarComoCompletada();
            return true;
        }
        return false;
    }

    // Mostrar actividades enumeradas (para uso desde UI)
    public void mostrarActividadesEnumeradas(Cultivo cultivo) {
        List<Actividad> actividades = cultivo.getActividades();
        if (actividades.isEmpty()) {
            System.out.println("Este cultivo no tiene actividades registradas.");
            return;
        }

        for (int i = 0; i < actividades.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, actividades.get(i));
        }
    }
}
