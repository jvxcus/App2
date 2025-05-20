package ui;

import models.Cultivo;
import services.GestorCultivos;
import services.GestorParcelas;
import services.GestorActividades;
import utils.CSVParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class App2 {

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Uso: java App2 <archivo_csv>");
      return;
    }

    String rutaArchivo = args[0];
    List<Cultivo> cultivos = new ArrayList<>();

    // 1. Leer archivo CSV
    try {
      List<String> lineas = Files.readAllLines(Path.of(rutaArchivo));
      for (String linea : lineas) {
        if (linea.startsWith("Cultivo")) {
          cultivos.add(CSVParser.parseCultivo(linea));
        }
      }
      System.out.println("Cultivos cargados: " + cultivos.size());
    } catch (IOException e) {
      System.err.println("Error al leer el archivo: " + rutaArchivo);
      return;
    } catch (Exception e) {
      System.err.println("Error al parsear cultivos: " + e.getMessage());
      return;
    }

    // 2. Inicializar gestores
    GestorCultivos gestorCultivos = new GestorCultivos();
    gestorCultivos.setCultivos(cultivos);

    GestorParcelas gestorParcelas = new GestorParcelas();
    gestorParcelas.setParcelas(gestorParcelas.crearParcelasDesdeCultivos(cultivos));

    GestorActividades gestorActividades = new GestorActividades();

    // 3. Iniciar menú
    Scanner scanner = new Scanner(System.in);
    Menu menu = new Menu(scanner, gestorCultivos, gestorParcelas, gestorActividades);
    menu.iniciar();

    // 4. Guardar datos al salir
    try {
      List<String> lineas = CSVParser.generarLineasCSV(gestorCultivos.getCultivos());
      Files.write(Path.of(rutaArchivo), lineas);
      System.out.println("Cambios guardados en: " + rutaArchivo);
    } catch (IOException e) {
      System.err.println("Error al guardar el archivo: " + e.getMessage());
    }

    scanner.close();
  }
}
