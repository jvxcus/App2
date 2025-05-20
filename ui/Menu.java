package ui;

import models.Cultivo;
import models.EstadoCultivo;
import models.Parcela;
import services.GestorActividades;
import services.GestorCultivos;
import services.GestorParcelas;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final GestorCultivos gestorCultivos;
    private final GestorParcelas gestorParcelas;
    private final GestorActividades gestorActividades;

    public Menu(Scanner scanner, GestorCultivos gc, GestorParcelas gp, GestorActividades ga) {
        this.scanner = scanner;
        this.gestorCultivos = gc;
        this.gestorParcelas = gp;
        this.gestorActividades = ga;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n----- Menú Principal -----");
            System.out.println("[1] Gestión de Cultivos");
            System.out.println("[2] Gestión de Parcelas");
            System.out.println("[3] Gestión de Actividades");
            System.out.println("[4] Búsqueda y Reporte");
            System.out.println("[0] Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> menuCultivos();
                case "2" -> menuParcelas();
                case "3" -> menuActividades();
                case "4" -> menuBusqueda();
                case "0" -> salir = true;
                default -> System.out.println(" Opción inválida.");
            }
        }
    }

    private void menuCultivos() {
        System.out.println("\n--- Gestión de Cultivos ---");
        System.out.println("[1] Listar");
        System.out.println("[2] Crear");
        System.out.println("[3] Editar");
        System.out.println("[4] Eliminar");
        System.out.print("Seleccione: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> gestorCultivos.mostrarCultivosEnumerados();
            case "2" -> crearCultivo();
            case "3" -> editarCultivo();
            case "4" -> eliminarCultivo();
            default -> System.out.println("Opción inválida.");
        }
    }

    private void crearCultivo() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Variedad: ");
        String variedad = scanner.nextLine();
        System.out.print("Superficie (ha): ");
        double superficie = Double.parseDouble(scanner.nextLine());
        System.out.print("Código de Parcela: ");
        String parcela = scanner.nextLine();
        System.out.print("Fecha siembra (yyyy-MM-dd): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());
        System.out.print("Estado (ACTIVO, EN_RIESGO, COSECHADO): ");
        EstadoCultivo estado = EstadoCultivo.valueOf(scanner.nextLine().toUpperCase());

        gestorCultivos.crearCultivo(nombre, variedad, superficie, parcela, fecha, estado);
        System.out.println("✅ Cultivo creado.");
    }

    private void editarCultivo() {
        gestorCultivos.mostrarCultivosEnumerados();
        System.out.print("Seleccione el índice: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        Cultivo cultivo = gestorCultivos.obtenerCultivoPorIndice(idx);
        if (cultivo == null) {
            System.out.println(" Índice inválido.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva variedad: ");
        String variedad = scanner.nextLine();
        System.out.print("Nueva superficie: ");
        double superficie = Double.parseDouble(scanner.nextLine());
        System.out.print("Nuevo código de parcela: ");
        String parcela = scanner.nextLine();
        System.out.print("Nuevo estado: ");
        EstadoCultivo estado = EstadoCultivo.valueOf(scanner.nextLine().toUpperCase());

        gestorCultivos.editarCultivo(cultivo, nombre, variedad, superficie, parcela, estado);
        System.out.println(" Cultivo actualizado.");
    }

    private void eliminarCultivo() {
        gestorCultivos.mostrarCultivosEnumerados();
        System.out.print("Seleccione el índice: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        Cultivo cultivo = gestorCultivos.obtenerCultivoPorIndice(idx);
        if (cultivo == null || !gestorCultivos.eliminarCultivo(cultivo)) {
            System.out.println(" No se puede eliminar el cultivo.");
        } else {
            System.out.println(" Cultivo eliminado.");
        }
    }

    private void menuParcelas() {
        System.out.println("\n--- Gestión de Parcelas ---");
        System.out.println("[1] Listar");
        System.out.println("[2] Crear");
        System.out.println("[3] Editar");
        System.out.println("[4] Eliminar");
        System.out.print("Seleccione: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> gestorParcelas.mostrarParcelasEnumeradas();
            case "2" -> {
                System.out.print("Código: ");
                String codigo = scanner.nextLine();
                System.out.print("Tamaño: ");
                double tam = Double.parseDouble(scanner.nextLine());
                System.out.print("Ubicación: ");
                String ubicacion = scanner.nextLine();
                gestorParcelas.agregarParcela(codigo, tam, ubicacion);
                System.out.println(" Parcela agregada.");
            }
            case "3" -> {
                gestorParcelas.mostrarParcelasEnumeradas();
                System.out.print("Seleccione índice: ");
                int idx = Integer.parseInt(scanner.nextLine()) - 1;
                Parcela p = gestorParcelas.obtenerParcelaPorIndice(idx);
                if (p == null) {
                    System.out.println(" Índice inválido.");
                    return;
                }
                System.out.print("Nuevo tamaño: ");
                double tam = Double.parseDouble(scanner.nextLine());
                System.out.print("Nueva ubicación: ");
                String ubicacion = scanner.nextLine();
                gestorParcelas.editarParcela(p, tam, ubicacion);
                System.out.println("Parcela modificada.");
            }
            case "4" -> {
                gestorParcelas.mostrarParcelasEnumeradas();
                System.out.print("Seleccione índice: ");
                int idx = Integer.parseInt(scanner.nextLine()) - 1;
                Parcela p = gestorParcelas.obtenerParcelaPorIndice(idx);
                if (p == null || !gestorParcelas.eliminarParcela(p)) {
                    System.out.println("No se puede eliminar (¿cultivos activos?).");
                } else {
                    System.out.println("Parcela eliminada.");
                }
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    private void menuActividades() {
        gestorCultivos.mostrarCultivosEnumerados();
        System.out.print("Seleccione un cultivo: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        Cultivo cultivo = gestorCultivos.obtenerCultivoPorIndice(idx);
        if (cultivo == null) {
            System.out.println("Índice inválido.");
            return;
        }

        System.out.println("\n--- Actividades para " + cultivo.getNombre() + " ---");
        System.out.println("[1] Ver actividades");
        System.out.println("[2] Registrar actividad");
        System.out.println("[3] Marcar como completada");
        System.out.println("[4] Eliminar actividad");
        System.out.print("Seleccione: ");
        String op = scanner.nextLine();

        switch (op) {
            case "1" -> gestorActividades.mostrarActividadesEnumeradas(cultivo);
            case "2" -> {
                System.out.print("Tipo: ");
                String tipo = scanner.nextLine();
                System.out.print("Fecha (yyyy-MM-dd): ");
                LocalDate fecha = LocalDate.parse(scanner.nextLine());
                gestorActividades.registrarActividad(cultivo, tipo, fecha);
                System.out.println("Actividad registrada.");
            }
            case "3" -> {
                gestorActividades.mostrarActividadesEnumeradas(cultivo);
                System.out.print("Índice: ");
                int i = Integer.parseInt(scanner.nextLine()) - 1;
                if (gestorActividades.completarActividad(cultivo, i)) {
                    System.out.println("Actividad completada.");
                } else {
                    System.out.println("Índice inválido.");
                }
            }
            case "4" -> {
                gestorActividades.mostrarActividadesEnumeradas(cultivo);
                System.out.print("Índice: ");
                int i = Integer.parseInt(scanner.nextLine()) - 1;
                if (gestorActividades.eliminarActividad(cultivo, i)) {
                    System.out.println("Actividad eliminada.");
                } else {
                    System.out.println("Índice inválido.");
                }
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    private void menuBusqueda() {
        System.out.print("Buscar por nombre o variedad: ");
        String termino = scanner.nextLine();
        List<Cultivo> encontrados = gestorCultivos.buscarCultivos(termino);
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron coincidencias.");
        } else {
            encontrados.forEach(System.out::println);
        }
    }
}
