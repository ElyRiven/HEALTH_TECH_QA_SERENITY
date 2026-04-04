## Instrucciones para GitHub Copilot y agentes de IA

### Stack Tecnológico

- **Java v17**
- **Gradle v9.4.0**
- **Serenity BDD v4.2.34**
- **Cucumber v7.4.0**
- **JUnit 5**

### Contexto del Proyecto

- Proyecto de Automatización de pruebas E2E en Java.
- El proyecto utiliza Gradle como sistema de construcción.
- Framework principal: Serenity BDD.
- Patrón de diseño: Screenplay Model.
- Los casos de prueba se reconocen y ejecutan usando archivos `.feature` escritos en lenguaje Gherkin.

### Buenas Prácticas de Desarrollo

- Mantener el código limpio, modular y reutilizable.
- Seguir el patrón Screenplay para separar tareas, elementos UI, ejecutores, escenarios, etc.
- Escribir métodos y clases con una única responsabilidad.
- Evitar la duplicidad de código; reutilizar componentes y utilidades.
- Documentar métodos y clases con comentarios claros y concisos.
- Escribir pruebas independientes y repetibles.
- Validar los resultados esperados usando aserciones claras.
- Mantener la estructura de carpetas organizada según convenciones de Serenity y Gradle.
- Realizar revisiones de código antes de integrar cambios a la rama principal.

### Importaciones

- **No** se deben realizar importaciones directamente dentro del cuerpo del código (métodos, clases, etc.).
- Todas las importaciones deben declararse al inicio de cada archivo Java, antes de la declaración de la clase.
- Usar las importaciones dentro del código únicamente después de haberlas declarado al inicio.

### Lineamientos de Nomenclatura y Sintaxis

- Todas las variables y métodos deben nombrarse en **inglés**.
- Utilizar el formato **lowerCamelCase** para variables y métodos (ejemplo: `userName`, `inputCredentials`).
- Los nombres de clases deben usar PascalCase y estar en inglés (ejemplo: `LoginPage`).
- Usa los ejemplos del documento `.github/instructions/serenity-sintax.instructions.md` para definir la implementación de las pruebas.
- Los archivos de documentación como el README.md son la única excepción de idioma, los cuales deben estar redactados en españ

### Uso de Gherkin

- Los archivos `.feature` deben estar escritos en lenguaje Gherkin para describir los escenarios de prueba.
- Utilizar pasos claros y descriptivos en inglés.
- Mantener la consistencia en la redacción de los escenarios.

### Otros Lineamientos

- Seguir las convenciones de Serenity BDD y el patrón Screenplay.
- Seguir **siempre** esta estructura de directorios:
  NOMBRE_DEL_PROYECTO/
  ├── build.gradle # Configuración de dependencias y plugins de Gradle
  ├── serenity.properties # Nombre del proyecto de Serenity
  ├── gradle.properties # Propiedades globales de Gradle
  ├── settings.gradle # Nombre del proyecto Gradle
  └── src/
  └── test/
  ├── java/
  │ └── org/example/
  │ ├── hooks/ # Configuración de contexto para los escenarios
  │ │
  │ ├── questions/ # Preguntas que el actor puede hacer para obtener información del sistema
  │ │
  │ ├── runners/ # Suite runners (JUnit Platform + Cucumber engine)
  │ │
  │ ├── stepdefinitions/ # Step definitions: conectan los pasos Gherkin con las tareas del actor
  │ │ │
  │ │ └── Hooks/ # Hooks de inicialización de escenarios
  │ │
  │ ├── tasks/ # Tareas que el actor puede realizar (interacciones de alto nivel)
  │ │
  │ ├── ui/ # Elementos de la interfaz de usuario (Targets)
  │ │
  │ └── utils/ # Constantes y utilidades compartidas
  │
  └── resources/
  ├── features/ # Escenarios de prueba en lenguaje Gherkin
  │
  └── serenity.conf # Configuración de Serenity (driver, capturas, nombre del proyecto)

- Mantener dependencias actualizadas en el archivo `build.gradle`.
- Priorizar la legibilidad y mantenibilidad del código.
