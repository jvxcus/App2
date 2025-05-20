package services;

import models.Parcela;
import models.Cultivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class GestorParcelas {
    private List<Parcela> parcelas;

    public GestorParcelas() {
        this.parcelas = new ArrayList<>();
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public List<Parcela> crearParcelasDesdeCultivos(List<Cultivo> cultivos) {
        Map<String, Parcela> mapaParcelas = new HashMap<>();

        for (Cultivo cultivo : cultivos) {
            String codigo = cultivo.getParcela();
            if (codigo == null || codigo.isBlank()) continue;

            mapaParcelas.putIfAbsent(codigo, new Parcela(codigo, 0, "Desconocida"));
            mapaParcelas.get(codigo).agregarCultivo(cultivo);

        }

        return new ArrayList<>(mapaParcelas.values());
    }

    // Crear una nueva parcela
    public void agregarParcela(String codigo, double tamaño, String ubicacion) {
        parcelas.add(new Parcela(codigo, tamaño, ubicacion));
    }

    // Listar todas las parcelas
    public void mostrarParcelasEnumeradas() {
        if (parcelas.isEmpty()) {
            System.out.println("No hay parcelas registradas.");
            return;
        }

        for (int i = 0; i < parcelas.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, parcelas.get(i));
        }
    }

    // Buscar parcela por código exacto
    public Parcela buscarParcelaPorCodigo(String codigo) {
        for (Parcela p : parcelas) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    // Obtener parcela por índice (para UI)
    public Parcela obtenerParcelaPorIndice(int index) {
        if (index >= 0 && index < parcelas.size()) {
            return parcelas.get(index);
        }
        return null;
    }

    // Editar una parcela
    public void editarParcela(Parcela parcela, double nuevoTamaño, String nuevaUbicacion) {
        parcela.setTamaño(nuevoTamaño);
        parcela.setUbicacion(nuevaUbicacion);
    }

    // Eliminar una parcela solo si no tiene cultivos activos
    public boolean eliminarParcela(Parcela parcela) {
        if (parcela.tieneCultivosActivos()) {
            return false;
        }
        return parcelas.remove(parcela);
    }

    // Asignar un cultivo a una parcela (relación lógica)
    public boolean asignarCultivoAParcela(Parcela parcela, Cultivo cultivo) {
        if (!cultivo.getParcela().equalsIgnoreCase(parcela.getCodigo())) {
            cultivo.setParcela(parcela.getCodigo());
        }
        parcela.agregarCultivo(cultivo);
        return true;
    }

    // Eliminar cultivos de una parcela (en memoria)
    public void limpiarCultivosDeParcela(Parcela parcela) {
        parcela.getCultivos().clear();
    }
}
