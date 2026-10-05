package helpdesk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Lectura y escritura de las incidencias.
 * Formato: una por línea, "id;cerrada;descripción" (la descripción puede contener ';').
 */
public class ArchivoTickets {

    private final Path ruta;

    public ArchivoTickets(String ruta) {
        this(Path.of(ruta));
    }

    public ArchivoTickets(Path ruta) {
        this.ruta = ruta;
    }

    public Path getRuta() {
        return ruta;
    }

    /**
     * Lee todas las incidencias. Si el archivo no existe devuelve una lista vacía.
     * Si hay cualquier línea inválida no se devuelve nada (no hay cargas parciales).
     *
     * @throws IllegalArgumentException si algún dato es inválido (indica la línea)
     * @throws IOException              si no se puede leer el archivo
     */
    public List<Ticket> cargar() throws IOException {
        List<Ticket> resultado = new ArrayList<>();

        if (!Files.exists(ruta)) {
            return resultado;
        }

        List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).isBlank()) {
                continue; // las líneas vacías se ignoran
            }
            resultado.add(parsear(lineas.get(i), i + 1));
        }
        return resultado;
    }

    /** Sustituye el contenido anterior del archivo por la colección indicada. */
    public void guardar(List<Ticket> tickets) throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Ticket ticket : tickets) {
            lineas.add(ticket.getId() + ";" + ticket.estaCerrado() + ";" + ticket.getDescripcion());
        }
        Files.write(ruta, lineas, StandardCharsets.UTF_8);
    }

    private Ticket parsear(String linea, int numeroLinea) {
        String[] partes = linea.split(";", 3); // límite 3: la descripción puede llevar ';'
        if (partes.length != 3) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": formato incorrecto.");
        }

        int id;
        try {
            id = Integer.parseInt(partes[0].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": identificador no numérico.");
        }

        String estado = partes[1].trim();
        if (!estado.equals("true") && !estado.equals("false")) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": estado debe ser true o false.");
        }

        try {
            return new Ticket(id, partes[2], Boolean.parseBoolean(estado));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": " + e.getMessage());
        }
    }
}
