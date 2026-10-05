package helpdesk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Gestiona el listado de incidencias: creación, búsqueda y estadísticas. */
public class GestorTickets {

    private final List<Ticket> tickets;
    private int siguienteId;

    /** Crea un gestor vacío; la numeración empieza en 1. */
    public GestorTickets() {
        this(new ArrayList<>());
    }

    /** Crea un gestor con incidencias existentes no repetidas*/
    public GestorTickets(List<Ticket> existentes) {
        Set<Integer> vistos = new HashSet<>();
        for (Ticket ticket : existentes) {
            if (!vistos.add(ticket.getId())) {
                throw new IllegalArgumentException("Identificador repetido: " + ticket.getId());
            }
        }
        tickets = new ArrayList<>(existentes);
        siguienteId = vistos.stream().max(Integer::compare).orElse(0) + 1;
    }

    /** Crea una incidencia abierta;  */
    public Ticket crearTicket(String descripcion) {
        Ticket ticket = new Ticket(siguienteId, descripcion);
        tickets.add(ticket);
        siguienteId++;
        return ticket;
    }

    /** Devuelve la incidencia con ese id,  */
    public Ticket buscarTicket(int id) {
        return tickets.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    /** Devuelve la primera incidencia, */
    public Ticket primero() {
        return tickets.isEmpty() ? null : tickets.get(0);
    }

    /** Devuelve una copia de la lista  */
    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    /** Número total de incidencias. */
    public int getTotalIncidencias() {
        return tickets.size();
    }

    /** Número de incidencias abiertas. */
    public int getIncidenciasAbiertas() {
        return (int) tickets.stream().filter(t -> !t.estaCerrado()).count();
    }

    /** Número de incidencias cerradas. */
    public int getIncidenciasCerradas() {
        return getTotalIncidencias() - getIncidenciasAbiertas();
    }
}