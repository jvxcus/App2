public class App2 {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Uso: java App2 cultivos.csv");
      return;
    }

    String rutaArchivo = args[0];
    GestorCultivos gestor = new GestorCultivos();
    gestor.procesarArchivo(rutaArchivo);

    Menu menu = new Menu(gestor, rutaArchivo);
    menu.mostrarMenuPrincipal();
  }
}