import java.time.LocalDate;
import java.util.*;
import models.Cultivo;
import models.Parcela;
import java.io.IOException;
import java.util.Scanner;

public class GestorParcelas {
    private List<Parcela> parcelas;
    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public GestorParcelas() {
        this.parcelas = new ArrayList<>();
    }
    public List<Parcela> crearParcelasDesdeCultivos(List<Cultivo> cultivos) {
        Map<String, Parcela> mapaParcelas = new HashMap<>();

        for (Cultivo c : cultivos) {
            String codigoParcela = c.getParcela();
            if (codigoParcela == null || codigoParcela.trim().isEmpty()) continue;

            codigoParcela = codigoParcela.trim();

            // Si la parcela no existe, crearla con tamaño 0 y sin ubicación por ahora
            mapaParcelas.putIfAbsent(codigoParcela, new Parcela(codigoParcela, 0, null));

            // Agregar cultivo a la parcela
            Parcela parcela = mapaParcelas.get(codigoParcela);
            parcela.agregarCultivo(c);

            // Sumar superficie del cultivo al tamaño de la parcela
            double nuevoTamano = parcela.getTamaño() + c.getSuperficie();
            parcela.setTamaño(nuevoTamano);
        }

        return new ArrayList<>(mapaParcelas.values());
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }
    /**
     * y luego imprime los cultivos asignados a cada parcela.
     */
    public void imprimirCultivosPorParcelaConObjetos(List<Parcela> parcelas) {
        for (Parcela parcela : parcelas) {
            System.out.print("Parcela " + parcela.getNombre() + ": ");
            List<Cultivo> cultivos = parcela.getCultivos();

            if (cultivos.isEmpty()) {
                System.out.println("No tiene cultivos.");
                continue;
            }

            List<String> nombresCultivos = new ArrayList<>();
            for (Cultivo c : cultivos) {
                nombresCultivos.add(c.getNombre());
            }

            System.out.println(String.join(", ", nombresCultivos) + " (Tamaño total: " + parcela.getTamaño() + " ha)");
        }
    }

    public boolean eliminarParcela(String nombreParcela, List<Cultivo> cultivosGlobal) {
        nombreParcela = nombreParcela.trim();

        // Buscar la parcela en la lista de parcelas
        Parcela parcelaAEliminar = null;
        for (Parcela p : parcelas) {
            if (p.getNombre().equalsIgnoreCase(nombreParcela)) {
                parcelaAEliminar = p;
                break;
            }
        }

        if (parcelaAEliminar == null) {
            System.out.println("No se encontró la parcela con nombre: " + nombreParcela);
            return false;
        }

        // Revisar que todos los cultivos de la parcela estén en estado "COSECHADO"
        for (Cultivo c : parcelaAEliminar.getCultivos()) {
            if (!c.getEstado().equalsIgnoreCase("COSECHADO")) {
                System.out.println("No se puede eliminar la parcela porque tiene cultivos que no están en estado COSECHADO.");
                return false;
            }
        }

        // Eliminar cultivos asociados de la lista global de cultivos
        cultivosGlobal.removeAll(parcelaAEliminar.getCultivos());

        // Eliminar la parcela de la lista de parcelas
        parcelas.remove(parcelaAEliminar);

        System.out.println("Parcela '" + nombreParcela + "' eliminada correctamente junto con sus cultivos.");
        return true;
    }

    public void agregarParcelaConCultivo(Scanner scanner, GestorCultivos gestorCultivos) {
        System.out.print("Ingrese el nombre de la nueva parcela: ");
        String nombreParcela = scanner.nextLine().trim();

        System.out.print("Ingrese el tamaño de la parcela (en hectáreas): ");
        double tamano = Double.parseDouble(scanner.nextLine());

        System.out.print("Ingrese la ubicación de la parcela: ");
        String ubicacion = scanner.nextLine().trim();

        // Crear nueva parcela
        Parcela nuevaParcela = new Parcela(nombreParcela, tamano, ubicacion);

        System.out.println("Ahora debe agregar un cultivo para esta parcela:");

        System.out.print("Nombre del cultivo: ");
        String nombreCultivo = scanner.nextLine();

        System.out.print("Variedad del cultivo: ");
        String variedad = scanner.nextLine();

        System.out.print("Superficie del cultivo (en hectáreas): ");
        double superficie = Double.parseDouble(scanner.nextLine());

        System.out.print("Fecha de siembra (YYYY-MM-DD): ");
        LocalDate fechaSiembra = LocalDate.parse(scanner.nextLine());

        System.out.print("Estado del cultivo: ");
        String estado = scanner.nextLine();

        Cultivo cultivo = new Cultivo(nombreCultivo, variedad, superficie, nombreParcela, fechaSiembra, estado, new ArrayList<>());

        // Agregar cultivo a la parcela
        nuevaParcela.agregarCultivo(cultivo);

        // Registrar en la lista de cultivos general y persistirlo
        gestorCultivos.agregarCultivo(cultivo);

        // Agregar la parcela a la lista interna
        this.parcelas.add(nuevaParcela);

        System.out.println("Parcela y cultivo agregados correctamente.");
    }



}