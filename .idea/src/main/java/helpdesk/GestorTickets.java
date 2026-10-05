package helpdesk;

import java.util.ArrayList;
import java.util.List;

/** Colección, identificadores, creación, búsqueda y estadísticas. */
public class GestorTickets {

    private final List<Ticket> tickets;
    private int siguienteId;

    public GestorTickets() {
        tickets = new ArrayList<>();
        siguienteId = 1;
    }

    public Ticket crearTicket(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

        Ticket ticket = new Ticket(siguienteId, descripcion.trim());
        tickets.add(ticket);
        siguienteId++;
        return ticket;
    }

    public Ticket buscarTicket(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    public boolean cerrarTicket(int id) {
        Ticket ticket = buscarTicket(id);

        if (ticket == null) {
            return false;
        }

        return ticket.cerrar();
    }

    public int getTotalIncidencias() {
        return tickets.size();
    }

    public int getIncidenciasAbiertas() {
        int abiertas = 0;

        for (Ticket ticket : tickets) {
            if (!ticket.estaCerrado()) {
                abiertas++;
            }
        }

        return abiertas;
    }

    public int getIncidenciasCerradas() {
        int cerradas = 0;

        for (Ticket ticket : tickets) {
            if (ticket.estaCerrado()) {
                cerradas++;
            }
        }

        return cerradas;
    }
}