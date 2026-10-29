import java.util.ArrayList;
import java.util.List;

public class Receta {
    private String nombre;
    private List<Ingrediente> ingredientes;
    private String instrucciones;
    private int porciones;

    public Receta(String nombre, List<Ingrediente> ingredientes,
                  String instrucciones, int porciones) {
        this.nombre = nombre;
        this.ingredientes = new ArrayList<>(ingredientes);
        this.instrucciones = instrucciones;
        this.porciones = porciones;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPorciones() {
        return porciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public void setPorciones(int porciones) {
        this.porciones = porciones;
    }

    public void mostrarDetalles() {
        System.out.println("\n--- RECETA: " + nombre + " ---");
        System.out.println("Ingredientes:");

        for (Ingrediente ingrediente : ingredientes) {
            System.out.println("- " + ingrediente);
        }

        System.out.println("Instrucciones: " + instrucciones);
        System.out.println("Porciones: " + porciones);
    }
}
