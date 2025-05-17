
// Para lectura del CSV 
import java.io.BufferedReader;  
import java.io.FileReader;
import java.io.IOException;

public class App2 {
  private BufferedReader buffer;

  // Constructor
  public App2() {
    try {
      buffer = new BufferedReader(new FileReader("cultivos.csv"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Método para procesar el archivo
  public void procesarArchivo() {
    try (BufferedReader bufer = new BufferedReader(new FileReader("cultivos.csv"))) {
      // Agrega tu lógica para procesar el archivo aquí
      String linea;
      while ((linea = bufer.readLine()) != null) {
        System.out.println(linea);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Método principal
  public static void main(String[] args) {
    App2 app = new App2();
    app.procesarArchivo();
  }
}