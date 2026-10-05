package helpdesk;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Ampliación opcional: automatiza la comprobación de la persistencia. */
class ArchivoTicketsTest {

    @TempDir
    Path carpeta;

    @Test
    void archivoInexistenteDevuelveColeccionVacia() throws IOException {
        ArchivoTickets archivo = new ArchivoTickets(carpeta.resolve("no-existe.txt"));

        assertTrue(archivo.cargar().isEmpty());
    }

    @Test
    void guardarYCargar() throws IOException {
        ArchivoTickets archivo = new ArchivoTickets(carpeta.resolve("tickets.txt"));
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Falla el teclado");
        gestor.crearTicket("Router; sin luz; revisar").cerrar();

        archivo.guardar(gestor.getTickets());
        List<Ticket> cargados = archivo.cargar();

        assertEquals(2, cargados.size());
        assertEquals(1, cargados.get(0).getId());
        assertFalse(cargados.get(0).estaCerrado());
        assertEquals(2, cargados.get(1).getId());
        assertTrue(cargados.get(1).estaCerrado());
        assertEquals("Router; sin luz; revisar", cargados.get(1).getDescripcion());
    }

    @Test
    void lineaInvalida
            () throws IOException {
        Path ruta = carpeta.resolve("tickets.txt");
        Files.write(ruta, List.of("1;false;Correcta", "esto no es una línea válida"));
        ArchivoTickets archivo = new ArchivoTickets(ruta);

        assertThrows(IllegalArgumentException.class, archivo::cargar);
    }

    @Test
    void estadoDistinto() throws IOException {
        Path ruta = carpeta.resolve("tickets.txt");
        Files.write(ruta, List.of("1;quizás;Texto"));

        assertThrows(IllegalArgumentException.class, () -> new ArchivoTickets(ruta).cargar());
    }
}
