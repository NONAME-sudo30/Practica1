package helpdesk;

/** Datos, validación y estado de una incidencia. */
public class Ticket {

    private final int id;
    private final String descripcion;
    private boolean cerrado;

    /** Crea una incidencia abierta. */
    public Ticket(int id, String descripcion) {
        this(id, descripcion, false);
    }

    /** Crea una incidencia con estado indicado (usado al recuperar datos del archivo). */
    public Ticket(int id, String descripcion, boolean cerrado) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (descripcion.contains("\n") || descripcion.contains("\r")) {
            throw new IllegalArgumentException("La descripción debe ocupar una sola línea.");
        }
        this.id = id;
        this.descripcion = descripcion.trim();
        this.cerrado = cerrado;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean estaCerrado() {
        return cerrado;
    }

    /**
     * Cierra la incidencia.
     *
     * @return true si estaba abierta y se ha cerrado; false si ya estaba cerrada
     */
    public boolean cerrar() {
        if (cerrado) {
            return false;
        }
        cerrado = true;
        return true;
    }

    public String toString() {
        return id + " - " + descripcion + " - " + (cerrado ? "CERRADA" : "ABIERTA");
    }
}
