# HelpDesk: gestión de incidencias

Aplicación de consola en Java 17 para registrar, consultar, cerrar y guardar
las incidencias de un centro. Usa Maven y JUnit 5.

## Requisitos

- Java 17 o superior (JDK)
- Maven 3.x

## Ejecución

```
mvn test
mvn compile
java -cp target/classes helpdesk.AplicacionHelpDesk
```

- `mvn test` ejecuta las pruebas unitarias.
- `mvn compile` compila el proyecto en `target/classes`.
- El tercer comando arranca la aplicación (hay que ejecutarlo desde la carpeta
  del proyecto, para que encuentre `tickets.txt`).

## Menú de la aplicación

| Opción | Acción |
|---|---|
| 1 | Crear incidencia |
| 2 | Listar incidencias |
| 3 | Buscar incidencia por identificador |
| 4 | Cerrar incidencia |
| 5 | Mostrar estadísticas (total, abiertas y cerradas) |
| 6 | Guardar incidencias en `tickets.txt` |
| 0 | Salir |

**Importante:** no hay guardado automático. Hay que usar la opción 6 antes de
salir, o se pierden los cambios.

## Estructura del proyecto

```
helpdesk/
├── pom.xml
├── tickets.txt
└── src/
    ├── main/java/helpdesk/
    │   ├── Ticket.java
    │   ├── GestorTickets.java
    │   ├── ArchivoTickets.java
    │   └── AplicacionHelpDesk.java
    └── test/java/helpdesk/
        ├── TicketTest.java
        ├── GestorTicketsTest.java
        ├── EstadisticasTest.java
        └── ArchivoTicketsTest.java
```

## Clases

| Clase | Qué hace |
|---|---|
| `Ticket` | Guarda los datos de una incidencia (id, descripción y estado) y los valida. El id y la descripción no cambian; se cierra con `cerrar()`. |
| `GestorTickets` | Gestiona la colección: crea incidencias con id automático, las busca y calcula las estadísticas. |
| `ArchivoTickets` | Lee y escribe las incidencias en `tickets.txt`. |
| `AplicacionHelpDesk` | Muestra el menú, lee el teclado y coordina el resto de clases. |

## Formato de `tickets.txt`

Una incidencia por línea, con el formato `id;cerrada;descripción`.
La descripción puede contener `;`.

```
1;false;Falla el teclado
2;true;Sin conexión a Internet
```

## Reglas y decisiones

- El id debe ser positivo y la descripción no puede estar vacía ni ocupar
  más de una línea.
- Si una incidencia no es válida, no se añade y no se gasta el identificador.
- Al cargar el archivo, el siguiente id es el mayor recuperado más uno.
- Si el archivo no existe, se empieza con una lista vacía.
- Si el archivo tiene líneas inválidas o ids repetidos, el programa avisa y no
  arranca, sin modificar el archivo.

## Pruebas unitarias

| Clase de test | Qué comprueba |
|---|---|
| `TicketTest` | Estado inicial, cierre y validación de datos. |
| `GestorTicketsTest` | Ids consecutivos, búsqueda, creación inválida y recuperación de datos. |
| `EstadisticasTest` | Contadores de total, abiertas y cerradas. |
| `ArchivoTicketsTest` | Guardado, carga y rechazo de datos inválidos. |

La clase `AplicacionHelpDesk` (el menú por consola) no tiene pruebas automáticas.

## Limitaciones

- Sin guardado automático.
- No se pueden editar ni eliminar incidencias.
- La ruta del archivo es fija (`tickets.txt`).
