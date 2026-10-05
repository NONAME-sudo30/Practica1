package helpdesk;

public class Ticket {

    private final int id;
    private final String descripcion;
    private boolean cerrado;

    public Ticket(int id, String descripcion) {
        this(id, descripcion, false);
    }

    public Ticket(int id, String descripcion, boolean cerrado) {
        validarId(id);
        validarDescripcion(descripcion);

        this.id = id;
        this.descripcion = descripcion;
        this.cerrado = cerrado;
    }

    private static void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo.");
        }
    }

    private static void validarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("Descripción no válida.");
        }
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

    public boolean cerrar() {
        if (cerrado) {
            return false;
        }

        cerrado = true;
        return true;
    }
}