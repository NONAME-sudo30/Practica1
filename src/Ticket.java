
/**Datos validaciones e incidencias*/
public class Ticket {
    private final int id;
    private final String descripcion;
    private boolean cerrado;
    /** Crea un ticket nuevo*/
    public Ticket(int id, String descripcion) {
        this(id, descripcion, false);
    }
    /** Reconstruye un ticket */
    public Ticket(int id, String descripcion, boolean cerrado) {
        validarId(id);
        validarDescripcion(descripcion);
        this.id = id;
        this.descripcion = descripcion;
        this.cerrado = cerrado;
    }
    /**Excepciones*/

    private static void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo: " + id);
        }
    }
    private static void validarDescripcion(String descripcion) {
        if (descripcion == null) {
            throw new IllegalArgumentException("La descripción no puede ser nula.");
        }
        if (descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

    }
    /** Consultas*/
    public int getId() {
        return id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public boolean estaCerrado() {
        return cerrado;
    }
    public boolean estaAbierto() {
        return !cerrado;
    }
    public boolean cerrar() {
        if (cerrado) {
            return false;
        }
        cerrado = true;
        return true;
    }
}
