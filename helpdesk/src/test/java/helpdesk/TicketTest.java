package helpdesk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TicketTest {

    @Test
    void ticketNuevoAbierto() {
        Ticket ticket = new Ticket(1, "Falla el teclado");

        assertFalse(ticket.estaCerrado());
        assertEquals(1, ticket.getId());
        assertEquals("Falla el teclado", ticket.getDescripcion());
    }

    @Test
    void cerrarTrue() {
        Ticket ticket = new Ticket(1, "Falla el teclado");

        assertTrue(ticket.cerrar());
        assertTrue(ticket.estaCerrado());
    }

    @Test
    void cerrarDosVeces() {
        Ticket ticket = new Ticket(1, "Falla el teclado");
        ticket.cerrar();

        assertFalse(ticket.cerrar());
        assertTrue(ticket.estaCerrado());
    }

    @Test
    void rechazaDescripcionEnBlanco() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, "   "));
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, ""));
    }

    @Test
    void rechazaDescripcionNula() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, null));
    }

    @Test
    void rechazaIdentificadorCero() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(0, "Falla el teclado"));
    }

    @Test
    void rechazaIdentificadorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(-5, "Falla el teclado"));
    }
}
