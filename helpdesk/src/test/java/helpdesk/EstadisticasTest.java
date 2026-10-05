package helpdesk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EstadisticasTest {

    @Test
    void gestorVacio() {
        GestorTickets gestor = new GestorTickets();

        assertEquals(0, gestor.getTotalIncidencias());
        assertEquals(0, gestor.getIncidenciasAbiertas());
        assertEquals(0, gestor.getIncidenciasCerradas());
    }

    @Test
    void dosIncidenciasAbiertas() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Falla el teclado");
        gestor.crearTicket("Sin conexión a Internet");

        assertEquals(2, gestor.getTotalIncidencias());
        assertEquals(2, gestor.getIncidenciasAbiertas());
        assertEquals(0, gestor.getIncidenciasCerradas());
    }

    @Test
    void dosIncidenciasConUnaCerrada() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Falla el teclado");
        Ticket segundo = gestor.crearTicket("Sin conexión a Internet");
        segundo.cerrar();

        assertEquals(2, gestor.getTotalIncidencias());
        assertEquals(1, gestor.getIncidenciasAbiertas());
        assertEquals(1, gestor.getIncidenciasCerradas());
    }
}
