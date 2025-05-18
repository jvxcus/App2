import models.Parcela;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private GestorCultivos gestorCultivos;
    private GestorParcelas gestorParcelas;

    private String rutaArchivo;

    public Menu(GestorCultivos gestorCultivos, String rutaArchivo) {
        this.gestorCultivos = gestorCultivos;
        this.rutaArchivo = rutaArchivo;
        this.gestorParcelas = new GestorParcelas();
    }

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Sub menú de Cultivos");
            System.out.println("2. Sub menú de Parcelas");
            System.out.println("3. Sub menú de Actividades");
            System.out.println("4. Salir y Guardar");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    mostrarMenuCultivos();
                    break;
                case 2:
                    mostrarMenuParcelas();
                    break;
                case 3:
                    System.out.println("Sub menú de Actividades no implementado aún.");
                    break;
                case 4:
                    System.out.println("Guardando cultivos y saliendo...");
                    try {
                        gestorCultivos.guardarCultivos(rutaArchivo);
                    } catch (Exception e) {
                        System.out.println("Error al guardar cultivos: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    private void mostrarMenuCultivos() {
        int opcion;
        do {
            System.out.println("\n--- Menú Cultivos ---");
            System.out.println("1. Listar cultivos");
            System.out.println("2. Crear cultivo");
            System.out.println("3. Editar cultivo");
            System.out.println("4. Eliminar cultivo");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    gestorCultivos.mostrarCultivos();
                    break;
                case 2:
                    gestorCultivos.crearNuevoCultivo(scanner);
                    break;
                case 3:
                    gestorCultivos.editarCultivo(scanner);
                    break;
                case 4:
                    gestorCultivos.eliminarCultivo(scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
    private void eliminarParcela() {
        System.out.print("Ingrese el nombre de la parcela a eliminar: ");
        String nombre = scanner.nextLine();

        // Crear o actualizar lista de parcelas antes de eliminar
        List<Parcela> listaParcelas = gestorParcelas.crearParcelasDesdeCultivos(gestorCultivos.getCultivos());
        gestorParcelas.setParcelas(listaParcelas);

        gestorParcelas.eliminarParcela(nombre, gestorCultivos.getCultivos());
    }



    public void mostrarMenuParcelas() {
        int opcion;
        do {
            System.out.println("\n--- Menú Parcelas ---");
            System.out.println("1. Listar parcelas con sus cultivos");
            System.out.println("2. Agregar una parcela");
            System.out.println("3. Eliminar una parcela (solo si no tiene cultivos activos)");
            System.out.println("4. Editar una parcela");
            System.out.println("5. Asignar un cultivo a una parcela");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    List<Parcela> parcelas = gestorParcelas.crearParcelasDesdeCultivos(gestorCultivos.getCultivos());
                    gestorParcelas.imprimirCultivosPorParcelaConObjetos(parcelas);                    break;
                case 2:
                    gestorParcelas.agregarParcelaConCultivo(scanner, gestorCultivos);
                    break;
                case 3:
                    eliminarParcela();
                    break;
                case 4:
                    System.out.println("Función para editar parcela no implementada aún.");
                    // gestorParcelas.editarParcela(scanner);
                    break;
                case 5:
                    System.out.println("Función para asignar cultivo a parcela no implementada aún.");
                    // gestorParcelas.asignarCultivoAParcela(scanner, gestorCultivos.getCultivos());
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }


    private int leerEntero() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Ingrese un número entero: ");
            }
        }
    }
}
