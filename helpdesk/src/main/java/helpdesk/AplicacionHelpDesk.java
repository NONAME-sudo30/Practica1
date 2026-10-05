package helpdesk;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Menú, teclado, mensajes y coordinación. */
public class AplicacionHelpDesk {

    private static final String ARCHIVO = "tickets.txt";

    private final ArchivoTickets archivo;
    private final Scanner teclado;
    private GestorTickets gestor;

    public AplicacionHelpDesk(ArchivoTickets archivo) {
        this.archivo = archivo;
        this.teclado = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new AplicacionHelpDesk(new ArchivoTickets(ARCHIVO)).iniciar();
    }

    private void iniciar() {
        if (cargarDatos()) {
            ejecutar();
        }
        teclado.close();
    }

    /** Carga las incidencias; si hay un problema informa y devuelve false (sin tocar el archivo). */
    private boolean cargarDatos() {
        try {
            List<Ticket> recuperados = archivo.cargar();
            gestor = new GestorTickets(recuperados);
            if (!recuperados.isEmpty()) {
                System.out.println("Se han recuperado " + recuperados.size()
                        + " incidencias de " + archivo.getRuta() + ".");
            }
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Datos inválidos en " + archivo.getRuta() + ": " + e.getMessage());
        } catch (IOException e) {
            System.out.println("No se pudo leer " + archivo.getRuta() + ": " + e.getMessage());
        }
        System.out.println("El programa no puede arrancar. El archivo no se ha modificado.");
        return false;
    }

    private void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {
                case 1 -> crearIncidencia();
                case 2 -> listarIncidencias();
                case 3 -> buscarIncidencia();
                case 4 -> cerrarIncidencia();
                case 5 -> mostrarEstadisticas();
                case 6 -> guardarIncidencias();
                case 0 -> System.out.println("Fin del programa.");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n-----HELPDESK DEL CENTRO------------\n");
        System.out.println("1. Crear incidencia");
        System.out.println("2. Listar incidencias");
        System.out.println("3. Buscar incidencia por identificador");
        System.out.println("4. Cerrar incidencia");
        System.out.println("5. Mostrar estadísticas");
        System.out.println("6. Guardar incidencias");
        System.out.println("0. Salir");
        System.out.println("----------------------------------------");
    }

    /** Lee la opción del menú; un valor no numérico devuelve -1 (opción incorrecta). */
    private int leerOpcion() {
        System.out.print("Opción: ");
        try {
            return Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        } catch (NoSuchElementException e) {
            return 0; // fin de la entrada: salir de forma controlada
        }
    }

    /** Pide un entero y repite hasta que la entrada sea numérica. */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero.");
            }
        }
    }

    private void crearIncidencia() {
        System.out.print("Descripción: ");
        String descripcion = teclado.nextLine();

        try {
            Ticket ticket = gestor.crearTicket(descripcion);
            System.out.println("Incidencia creada con ID " + ticket.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("No se ha creado la incidencia: " + e.getMessage());
        }
    }

    private void listarIncidencias() {
        List<Ticket> tickets = gestor.getTickets();

        if (tickets.isEmpty()) {
            System.out.println("No hay incidencias.");
            return;
        }

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private void buscarIncidencia() {
        int id = leerEntero("ID: ");
        Ticket ticket = gestor.buscarTicket(id);

        if (ticket == null) {
            System.out.println("La incidencia no existe.");
        } else {
            System.out.println(ticket);
        }
    }

    private void cerrarIncidencia() {
        int id = leerEntero("ID: ");
        Ticket ticket = gestor.buscarTicket(id);

        if (ticket == null) {
            System.out.println("La incidencia no existe.");
        } else if (ticket.cerrar()) {
            System.out.println("Incidencia cerrada.");
        } else {
            System.out.println("La incidencia ya estaba cerrada.");
        }
    }

    private void mostrarEstadisticas() {
        System.out.println("Total: " + gestor.getTotalIncidencias());
        System.out.println("Abiertas: " + gestor.getIncidenciasAbiertas());
        System.out.println("Cerradas: " + gestor.getIncidenciasCerradas());
    }

    private void guardarIncidencias() {
        try {
            archivo.guardar(gestor.getTickets());
            System.out.println("Incidencias guardadas en " + archivo.getRuta() + ".");
        } catch (IOException e) {
            System.out.println("No se pudieron guardar las incidencias: " + e.getMessage());
        }
    }
}
