package helpdesk;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class GestorTicketsTest {

    @Test
    void ListaVacia() {
        GestorTickets gestor = new GestorTickets();

        assertTrue(gestor.getTickets().isEmpty());
        assertEquals(0, gestor.getTotalIncidencias());
    }

    @Test
    void losIdentificadoresSonConsecutivos() {
        GestorTickets gestor = new GestorTickets();

        Ticket primero = gestor.crearTicket("Falla el teclado");
        Ticket segundo = gestor.crearTicket("Sin conexión a Internet");

        assertEquals(1, primero.getId());
        assertEquals(2, segundo.getId());
    }

    @Test
    void buscarDevuelveElMismoObjeto() {
        GestorTickets gestor = new GestorTickets();
        Ticket creado = gestor.crearTicket("Falla el teclado");

        assertSame(creado, gestor.buscarTicket(creado.getId()));
    }

    @Test
    void buscarNull() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Falla el teclado");

        assertNull(gestor.buscarTicket(99));
    }

    @Test
    void creacionInvalida() {
        GestorTickets gestor = new GestorTickets();

        assertThrows(IllegalArgumentException.class, () -> gestor.crearTicket("   "));
        assertEquals(0, gestor.getTotalIncidencias());

        Ticket siguiente = gestor.crearTicket("Falla el teclado");
        assertEquals(1, siguiente.getId(), "El fallo no debe consumir identificador");
    }



    @Test
    void RecuperarDatos() {
        List<Ticket> existentes = List.of(new Ticket(1, "A"), new Ticket(5, "B", true));
        GestorTickets gestor = new GestorTickets(existentes);
        assertEquals(6, gestor.crearTicket("C").getId());
    }

    @Test
    void identificadoresRepetidos() {
        List<Ticket> existentes = List.of(new Ticket(1, "A"), new Ticket(1, "B"));

        assertThrows(IllegalArgumentException.class, () -> new GestorTickets(existentes));
    }
}
