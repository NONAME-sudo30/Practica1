package helpdesk;

import java.util.List;
import java.util.Scanner;

public class AplicacionHelpDesk {

    private final GestorTickets gestor;
    private final Scanner teclado;

    public AplicacionHelpDesk() {
        gestor = new GestorTickets();
        teclado = new Scanner(System.in);
    }

    public static void main(String[] args) {
        AplicacionHelpDesk app = new AplicacionHelpDesk();
        app.ejecutar();
    }

    private void ejecutar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> crearIncidencia();
                case 2 -> listarIncidencias();
                case 3 -> buscarIncidencia();
                case 4 -> cerrarIncidencia();
                case 5 -> mostrarEstadisticas();
                case 6 -> listarAbiertas();
                case 7 -> listarCerradas();
                case 0 -> System.out.println("Fin del programa");
                default -> System.out.println("Opción incorrecta");
            }
        } while (opcion != 0);

        teclado.close();
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // en el menú cae en "Opción incorrecta"
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine();
    }

    public void mostrarMenu() {
        System.out.println("\nHELPDESK DEL CENTRO");
        System.out.println("1. Crear incidencia");
        System.out.println("2. Listar incidencias");
        System.out.println("3. Buscar incidencia");
        System.out.println("4. Cerrar incidencia");
        System.out.println("5. Mostrar estadísticas");
        System.out.println("6. Listar incidencias abiertas");
        System.out.println("7. Listar incidencias cerradas");
        System.out.println("0. Salir");
    }

    private void mostrarTicket(Ticket ticket) {
        System.out.println(
                ticket.getId() + " - "
                        + ticket.getDescripcion()
                        + " - "
                        + (ticket.estaCerrado() ? "CERRADA" : "ABIERTA"));
    }

    private void crearIncidencia() {
        String descripcion = leerTexto("Descripción: ");

        try {
            Ticket ticket = gestor.crearTicket(descripcion);
            System.out.println("Incidencia creada con ID " + ticket.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarIncidencias() {
        List<Ticket> tickets = gestor.getTickets();

        if (tickets.isEmpty()) {
            System.out.println("No hay incidencias.");
            return;
        }

        for}