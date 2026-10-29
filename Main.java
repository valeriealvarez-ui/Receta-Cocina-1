import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static final int MAX_RECETAS = 100;

    static class Ingrediente {
        String nombre;
        double cantidad;
        String unidad;

        Ingrediente(String nombre, double cantidad, String unidad) {
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.unidad = unidad;
        }
    }

    static class Receta {
        String nombre;
        ArrayList<Ingrediente> ingredientes;
        String instrucciones;
        int porciones;

        Receta(String nombre, ArrayList<Ingrediente> ingredientes,
               String instrucciones, int porciones) {
            this.nombre = nombre;
            this.ingredientes = new ArrayList<>(ingredientes);
            this.instrucciones = instrucciones;
            this.porciones = porciones;
        }
    }

    static ArrayList<Receta> recetas = new ArrayList<>();

    public static void main(String[] args) {
        cargarRecetas();

        int opcion;

        do {
            System.out.println("\n===== GESTION DE RECETAS =====");
            System.out.println("1. Registrar receta");
            System.out.println("2. Consultar receta");
            System.out.println("3. Actualizar receta");
            System.out.println("4. Listar recetas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarReceta();
                    break;
                case 2:
                    consultarReceta();
                    break;
                case 3:
                    actualizarReceta();
                    break;
                case 4:
                    listarRecetas();
                    break;
                case 5:
                    evento("Sistema cerrado");
                    System.out.println("Gracias por usar el sistema.");
                    break;
                default:
                    System.out.println("Opcion incorrecta.");
                    evento("Opcion de menu invalida");
            }
        } while (opcion != 5);

        sc.close();
    }

    static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Error. Ingrese un numero entero: ");
                evento("Entrada numerica invalida");
            }
        }
    }

    static void cargarRecetas() {
        ArrayList<Ingrediente> i;

        i = new ArrayList<>();
        i.add(new Ingrediente("Harina", 200, "gramos"));
        i.add(new Ingrediente("Azucar", 150, "gramos"));
        i.add(new Ingrediente("Cacao", 50, "gramos"));
        i.add(new Ingrediente("Huevos", 2, "unidades"));
        recetas.add(new Receta("Torta de chocolate", i,
                "Mezclar los ingredientes y hornear durante 30 minutos.", 6));

        i = new ArrayList<>();
        i.add(new Ingrediente("Harina", 150, "gramos"));
        i.add(new Ingrediente("Leche", 200, "ml"));
        i.add(new Ingrediente("Huevo", 1, "unidad"));
        recetas.add(new Receta("Arepuelas", i,
                "Mezclar y cocinar en una sarten.", 4));

        i = new ArrayList<>();
        i.add(new Ingrediente("Espaguetis", 250, "gramos"));
        i.add(new Ingrediente("Carne molida", 200, "gramos"));
        i.add(new Ingrediente("Tomate", 2, "unidades"));
        recetas.add(new Receta("Pasta con albondigas", i,
                "Cocinar la pasta y preparar la salsa con carne y tomate.", 3));

        i = new ArrayList<>();
        i.add(new Ingrediente("Limones", 4, "unidades"));
        i.add(new Ingrediente("Agua", 1, "litro"));
        i.add(new Ingrediente("Azucar", 50, "gramos"));
        recetas.add(new Receta("Limonada", i,
                "Exprimir los limones y mezclar con agua y azucar.", 4));

        i = new ArrayList<>();
        i.add(new Ingrediente("Aguacate", 2, "unidades"));
        i.add(new Ingrediente("Tomate", 1, "unidad"));
        i.add(new Ingrediente("Limon", 1, "unidad"));
        recetas.add(new Receta("Guacamole", i,
                "Triturar el aguacate y mezclar con tomate y limon.", 3));

        evento("Recetas de ejemplo cargadas");
    }

    static void registrarReceta() {
        if (recetas.size() >= MAX_RECETAS) {
            System.out.println("Se alcanzo el limite de recetas.");
            evento("Capacidad maxima alcanzada");
            return;
        }

        System.out.print("Nombre de la receta: ");
        String nombre = sc.nextLine().trim();

        if (nombre.isEmpty() || buscar(nombre) != null) {
            System.out.println("Nombre vacio o receta ya existente.");
            evento("Nombre invalido o receta duplicada");
            return;
        }

        System.out.print("Cantidad de ingredientes: ");
        int cantidadIngredientes = leerEntero();

        if (cantidadIngredientes <= 0) {
            System.out.println("Debe ingresar al menos un ingrediente.");
            return;
        }

        ArrayList<Ingrediente> ingredientes = new ArrayList<>();

        for (int x = 0; x < cantidadIngredientes; x++) {
            System.out.println("Ingrediente " + (x + 1));

            System.out.print("Nombre: ");
            String nom = sc.nextLine().trim();

            System.out.print("Cantidad: ");
            double cantidad;

            try {
                cantidad = Double.parseDouble(sc.nextLine().trim());

                if (!Double.isFinite(cantidad) || cantidad <= 0) {
                    System.out.println("Cantidad invalida.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar una cantidad numerica.");
                evento("Cantidad de ingrediente invalida");
                return;
            }

            System.out.print("Unidad (gramos, ml, unidades): ");
            String unidad = sc.nextLine().trim();

            if (nom.isEmpty() || unidad.isEmpty()) {
                System.out.println("El nombre y la unidad son obligatorios.");
                return;
            }

            ingredientes.add(new Ingrediente(nom, cantidad, unidad));
        }

        System.out.print("Instrucciones: ");
        String instrucciones = sc.nextLine().trim();

        if (instrucciones.isEmpty()) {
            System.out.println("Las instrucciones son obligatorias.");
            return;
        }

        System.out.print("Numero de porciones: ");
        int porciones = leerEntero();

        if (porciones <= 0) {
            System.out.println("Las porciones deben ser mayores que cero.");
            return;
        }

        recetas.add(new Receta(nombre, ingredientes, instrucciones, porciones));
        System.out.println("Receta registrada correctamente.");
        evento("Receta registrada: " + nombre);
    }

    static void consultarReceta() {
        System.out.print("Nombre de la receta: ");
        String nombre = sc.nextLine().trim();

        Receta r = buscar(nombre);

        if (r == null) {
            System.out.println("La receta no existe.");
            evento("Receta no encontrada: " + nombre);
        } else {
            mostrarReceta(r);
            evento("Receta consultada: " + nombre);
        }
    }

    static void actualizarReceta() {
        System.out.print("Nombre de la receta que desea actualizar: ");
        String nombre = sc.nextLine().trim();

        Receta r = buscar(nombre);

        if (r == null) {
            System.out.println("La receta no existe.");
            evento("Actualizacion fallida: " + nombre);
            return;
        }

        System.out.println("1. Cambiar instrucciones");
        System.out.println("2. Cambiar porciones");
        System.out.print("Seleccione: ");

        int opcion = leerEntero();

        if (opcion == 1) {
            System.out.print("Nuevas instrucciones: ");
            String nuevas = sc.nextLine().trim();

            if (nuevas.isEmpty()) {
                System.out.println("Las instrucciones no pueden estar vacias.");
                return;
            }

            r.instrucciones = nuevas;
            System.out.println("Instrucciones actualizadas.");
            evento("Instrucciones actualizadas: " + nombre);

        } else if (opcion == 2) {
            System.out.print("Nuevas porciones: ");
            int porciones = leerEntero();

            if (porciones <= 0) {
                System.out.println("Las porciones deben ser positivas.");
                return;
            }

            r.porciones = porciones;
            System.out.println("Porciones actualizadas.");
            evento("Porciones actualizadas: " + nombre);

        } else {
            System.out.println("Opcion incorrecta.");
        }
    }

    static void listarRecetas() {
        if (recetas.isEmpty()) {
            System.out.println("No hay recetas registradas.");
            return;
        }

        System.out.println("\n===== RECETAS REGISTRADAS =====");

        for (Receta r : recetas) {
            mostrarReceta(r);
        }

        evento("Listado de recetas consultado");
    }

    static Receta buscar(String nombre) {
        for (Receta r : recetas) {
            if (r.nombre.equalsIgnoreCase(nombre)) {
                return r;
            }
        }
        return null;
    }

    static void mostrarReceta(Receta r) {
        System.out.println("\nNombre: " + r.nombre);
        System.out.println("Ingredientes:");

        for (Ingrediente i : r.ingredientes) {
            System.out.println("- " + i.nombre + ": "
                    + i.cantidad + " " + i.unidad);
        }

        System.out.println("Instrucciones: " + r.instrucciones);
        System.out.println("Porciones: " + r.porciones);
    }

    static void evento(String mensaje) {
        try (FileWriter archivo = new FileWriter("eventos.txt", true)) {
            archivo.write(LocalDateTime.now() + " - " + mensaje
                    + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("No se pudo guardar el evento.");
        }
    }
}
