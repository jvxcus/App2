import java.io.*;
import java.time.LocalDate;
import java.util.*;

import models.Actividad;
import models.Cultivo;
import models.Parcela;
import utils.parser;

public class GestorCultivos {
    private List<Cultivo> cultivos = new ArrayList<>();
    private List<Parcela> parcelas = new ArrayList<>();

    public List<Cultivo> getCultivos() {
        return cultivos;
    }
    public List<Parcela> getParcelas() {
        return parcelas;
    }
    public void procesarArchivo(String ruta) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = buffer.readLine()) != null) {
                Cultivo cultivo = parser.parsearLinea(linea);
                cultivos.add(cultivo);
            }
            System.out.println("Se cargaron " + cultivos.size() + " cultivos.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarCultivos() {
        if (cultivos.isEmpty()) {
            System.out.println("No hay cultivos para mostrar.");
            return;
        }
        System.out.println("=== Lista de Cultivos ===");
        for (Cultivo c : cultivos) {
            System.out.println(c);
        }
    }

    public void buscarCultivoPorNombre(String nombreBuscado) {
        boolean encontrado = false;
        for (Cultivo c : cultivos) {
            if (c.getNombre().equalsIgnoreCase(nombreBuscado)) {
                System.out.println("=== Cultivo encontrado ===");
                System.out.println(c);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró un cultivo con ese nombre.");
        }
    }

    public void crearNuevoCultivo(Scanner scanner) {
        System.out.println("=== Crear nuevo cultivo ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Variedad: ");
        String variedad = scanner.nextLine();

        System.out.print("Superficie (ha): ");
        double superficie = Double.parseDouble(scanner.nextLine());

        System.out.print("Parcela: ");
        String parcela = scanner.nextLine();

        System.out.print("Fecha siembra (YYYY-MM-DD): ");
        LocalDate fechaSiembra = LocalDate.parse(scanner.nextLine());

        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        List<Actividad> actividades = new ArrayList<>();

        Cultivo nuevoCultivo = new Cultivo(nombre, variedad, superficie, parcela, fechaSiembra, estado, actividades);
        cultivos.add(nuevoCultivo);

        System.out.println("Nuevo cultivo creado y agregado a la lista.");
    }

    public void editarCultivo(Scanner scanner) {
        System.out.print("Ingrese el nombre del cultivo a editar: ");
        String nombreBuscado = scanner.nextLine();
        Cultivo cultivoEditar = null;

        for (Cultivo c : cultivos) {
            if (c.getNombre().equalsIgnoreCase(nombreBuscado)) {
                cultivoEditar = c;
                break;
            }
        }

        if (cultivoEditar == null) {
            System.out.println("No se encontró un cultivo con ese nombre.");
            return;
        }

        System.out.println("Deje vacío para mantener el valor actual.");
        System.out.println("Nombre actual: " + cultivoEditar.getNombre());
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isBlank()) cultivoEditar.setNombre(nuevoNombre);

        System.out.println("Variedad actual: " + cultivoEditar.getVariedad());
        System.out.print("Nueva variedad: ");
        String nuevaVariedad = scanner.nextLine();
        if (!nuevaVariedad.isBlank()) cultivoEditar.setVariedad(nuevaVariedad);

        System.out.println("Superficie actual: " + cultivoEditar.getSuperficie());
        System.out.print("Nueva superficie: ");
        String nuevaSuperficieStr = scanner.nextLine();
        if (!nuevaSuperficieStr.isBlank()) {
            double nuevaSuperficie = Double.parseDouble(nuevaSuperficieStr);
            cultivoEditar.setSuperficie(nuevaSuperficie);
        }

        System.out.println("Parcela actual: " + cultivoEditar.getParcela());
        System.out.print("Nueva parcela: ");
        String nuevaParcela = scanner.nextLine();
        if (!nuevaParcela.isBlank()) cultivoEditar.setParcela(nuevaParcela);

        System.out.println("Fecha siembra actual: " + cultivoEditar.getFechaSiembra());
        System.out.print("Nueva fecha siembra (YYYY-MM-DD): ");
        String nuevaFechaStr = scanner.nextLine();
        if (!nuevaFechaStr.isBlank()) {
            LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr);
            cultivoEditar.setFechaSiembra(nuevaFecha);
        }

        System.out.println("Estado actual: " + cultivoEditar.getEstado());
        System.out.print("Nuevo estado: ");
        String nuevoEstado = scanner.nextLine();
        if (!nuevoEstado.isBlank()) cultivoEditar.setEstado(nuevoEstado);

        System.out.println("Cultivo actualizado correctamente.");
    }
    public void eliminarCultivo(Scanner scanner) {
        System.out.print("Ingrese el nombre del cultivo a eliminar: ");
        String nombreBuscado = scanner.nextLine();
        Cultivo cultivoEliminar = null;

        for (Cultivo c : cultivos) {
            if (c.getNombre().equalsIgnoreCase(nombreBuscado)) {
                cultivoEliminar = c;
                break;
            }
        }

        if (cultivoEliminar == null) {
            System.out.println("No se encontró un cultivo con ese nombre.");
            return;
        }

        System.out.print("¿Está seguro que desea eliminar el cultivo '" + cultivoEliminar.getNombre() + "'? (s/n): ");
        String confirmar = scanner.nextLine();

        if (confirmar.equalsIgnoreCase("s")) {
            cultivos.remove(cultivoEliminar);
            System.out.println("Cultivo eliminado correctamente.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }


    public void guardarCultivos(String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (Cultivo c : cultivos) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cultivo").append(",");
                sb.append(c.getNombre()).append(",");
                sb.append(c.getVariedad()).append(",");
                sb.append(c.getSuperficie()).append(",");
                sb.append(c.getParcela()).append(",");
                sb.append(c.getFechaSiembra()).append(",");
                sb.append(c.getEstado()).append(",");

                List<Actividad> acts = c.getActividades();
                sb.append("[");
                for (int i = 0; i < acts.size(); i++) {
                    sb.append("\"").append(acts.get(i).toString()).append("\"");
                    if (i < acts.size() - 1) sb.append(", ");
                }
                sb.append("]");

                writer.write(sb.toString());
                writer.newLine();
            }
            System.out.println("Cultivos guardados en " + ruta);
        } catch (IOException e) {
            System.out.println("Error al guardar cultivos: " + e.getMessage());
        }
    }
    public void agregarCultivo(Cultivo cultivo) {
        cultivos.add(cultivo);
    }
}
