# HealthTech QA — Automatización E2E con Serenity BDD

## Descripción

Proyecto de automatización de pruebas End-to-End (E2E) para la aplicación **HealthTech MVP**, un sistema de triaje hospitalario basado en el Protocolo de Manchester. Cubre los flujos principales de registro de pacientes, registro de signos vitales, clasificación automática de criticidad y visualización en el Dashboard.

Las pruebas automatizan los 13 casos de prueba definidos en la matriz `TEST_CASES.md`, validando el comportamiento esperado del sistema tanto en escenarios exitosos como en escenarios de error.

---

## Stack Tecnológico

| Tecnología                        | Versión  | Rol                                        |
| --------------------------------- | -------- | ------------------------------------------ |
| Java                              | 17       | Lenguaje principal de la automatización    |
| Gradle                            | 9.4+     | Sistema de construcción y ejecución        |
| Serenity BDD                      | 4.2.34   | Framework de automatización y reportes     |
| Cucumber                          | 7.4.0    | Motor de escenarios BDD (lenguaje Gherkin) |
| JUnit 5 (Platform Suite)          | 5.10.2   | Suite runner de los escenarios             |
| Selenium WebDriver (ChromeDriver) | Incluido | Control del navegador                      |
| PostgreSQL JDBC Driver            | 42.7.3   | Limpieza de base de datos entre escenarios |

---

## Estructura del Proyecto

```
HEALTH_TECH_QA_SERENITY/
│
├── build.gradle                  # Dependencias y plugins de Gradle
├── gradle.properties             # Propiedades globales de Gradle
├── serenity.properties           # Nombre del proyecto en el reporte Serenity
├── settings.gradle               # Nombre del proyecto Gradle
│
└── src/
    └── test/
        ├── java/
        │   └── org/example/
        │       │
        │       ├── hooks/
        │       │   └── OpenBrowser.java            # Tarea de apertura del navegador
        │       │
        │       ├── questions/
        │       │   ├── TheAlertText.java            # Texto del cuadro de notificación
        │       │   ├── TheCriticality.java          # Criticidad de un paciente en el Dashboard
        │       │   ├── TheCriticalityOrder.java     # Lista de criticidades en la tabla
        │       │   ├── TheCurrentUrl.java           # URL actual del navegador
        │       │   └── TheVitalSignsError.java      # Texto del error en formulario de signos vitales
        │       │
        │       ├── runners/
        │       │   └── TestRunner.java              # Suite runner JUnit Platform + Cucumber
        │       │
        │       ├── stepdefinitions/
        │       │   ├── Hooks/
        │       │   │   └── Hook.java                # @Before (stage) y @After (limpieza de DB)
        │       │   ├── DashboardStepDefinitions.java
        │       │   ├── PatientRegistrationStepDefinitions.java
        │       │   └── VitalSignsStepDefinitions.java
        │       │
        │       ├── tasks/
        │       │   ├── FillPatientForm.java         # Rellena los campos del formulario de paciente
        │       │   ├── FillVitalSignsForm.java      # Rellena los campos del formulario de signos vitales
        │       │   ├── NavigateTo.java              # Navega a una URL
        │       │   ├── RegisterPatient.java         # Flujo completo de registro de paciente
        │       │   └── RegisterVitalSigns.java      # Flujo completo de registro de signos vitales
        │       │
        │       ├── ui/
        │       │   ├── DashboardUI.java             # Targets del Dashboard
        │       │   ├── PatientFormUI.java           # Targets del formulario de paciente
        │       │   └── VitalSignsFormUI.java        # Targets del formulario de signos vitales
        │       │
        │       └── utils/
        │           ├── Constants.java               # URLs base y nombre del actor
        │           └── DatabaseCleaner.java         # Trunca las tablas de la BD tras cada escenario
        │
        └── resources/
            ├── features/
            │   ├── 00_tc012_empty_dashboard.feature # TC-012: Dashboard vacío (debe ejecutarse primero)
            │   ├── dashboard.feature                # TC-006 al TC-011 y TC-013: Dashboard y clasificación
            │   ├── patient_registration.feature     # TC-001 al TC-003: Registro de pacientes
            │   └── vital_signs.feature              # TC-004 al TC-005: Registro de signos vitales
            │
            └── serenity.conf                        # Configuración del driver, timeouts y capturas
```

---

## Casos de Prueba Automatizados

| ID     | Descripción                                                   | Feature                            |
| ------ | ------------------------------------------------------------- | ---------------------------------- |
| TC-001 | Registro exitoso de nuevo paciente con datos válidos          | `patient_registration.feature`     |
| TC-002 | Registro falla cuando campos obligatorios están vacíos (x4)   | `patient_registration.feature`     |
| TC-003 | Registro falla con identificación duplicada                   | `patient_registration.feature`     |
| TC-004 | Registro exitoso de signos vitales con valores válidos        | `vital_signs.feature`              |
| TC-005 | Registro falla con valores fuera de rango (saturación O2)     | `vital_signs.feature`              |
| TC-006 | Clasificación automática asigna criticidad "Emergencia"       | `dashboard.feature`                |
| TC-007 | Clasificación automática asigna criticidad "Muy Urgente"      | `dashboard.feature`                |
| TC-008 | Clasificación automática asigna criticidad "Urgente"          | `dashboard.feature`                |
| TC-009 | Clasificación automática asigna criticidad "Menos Urgente"    | `dashboard.feature`                |
| TC-010 | Clasificación automática asigna criticidad "No Urgente"       | `dashboard.feature`                |
| TC-011 | Dashboard muestra pacientes ordenados por criticidad          | `dashboard.feature`                |
| TC-012 | Dashboard muestra mensaje cuando no hay pacientes registrados | `00_tc012_empty_dashboard.feature` |
| TC-013 | Notificación visual al registrar nuevo paciente con signos    | `dashboard.feature`                |

**Total: 16 escenarios** (TC-002 genera 4 escenarios mediante Scenario Outline).

---

## Prerrequisitos

Antes de ejecutar las pruebas, asegúrate de que los siguientes servicios estén corriendo:

| Servicio      | URL / Puerto            | Descripción             |
| ------------- | ----------------------- | ----------------------- |
| Frontend      | `http://localhost:5173` | Aplicación React (Vite) |
| Backend       | `http://localhost:3000` | API REST Node.js        |
| Base de datos | `localhost:5433`        | PostgreSQL (via Docker) |

> Para levantar el stack completo:
>
> ```bash
> cd ../HEALTH_TECH_MVP
> docker compose up -d
> ```

---

## Ejecución del Proyecto

### Ejecutar todas las pruebas

```bash
gradle clean test
```

o si no tienes Gradle instalado globalmente:

```bash
./gradlew clean test
```

> El reporte HTML de Serenity se genera automáticamente en:
> `target/site/serenity/index.html`

### Variables de entorno para CI/CD

La conexión a la base de datos para la limpieza automática entre escenarios puede configurarse con las siguientes propiedades de sistema:

| Propiedad     | Valor por defecto                         | Descripción                  |
| ------------- | ----------------------------------------- | ---------------------------- |
| `db.url`      | `jdbc:postgresql://localhost:5433/Triage` | JDBC URL de la base de datos |
| `db.user`     | `postgres`                                | Usuario de la BD             |
| `db.password` | `your_password_here`                      | Contraseña de la BD          |

Ejemplo para sobreescribir en CI/CD:

```bash
./gradlew clean test \
  -Ddb.url=jdbc:postgresql://db:5432/Triage \
  -Ddb.user=postgres \
  -Ddb.password=my_secret_password
```

### Configuración headless

La ejecución es **headless por defecto** (sin ventana de navegador visible), lo cual es ideal para pipelines de CI/CD. Para depuración local con ventana visible:

```bash
./gradlew clean test -Dheadless.mode=false
```

---

## Reporte Serenity BDD

Tras la ejecución, Serenity genera un reporte HTML detallado que incluye:

- Resultado de cada escenario (pasado / fallido / pendiente)
- Capturas de pantalla en caso de fallo (`FOR_FAILURES`)
- Pasos de ejecución de cada escenario
- Estadísticas de cobertura por Historia de Usuario

Abrir el reporte:

```bash
# Linux / macOS
xdg-open target/site/serenity/index.html

# O simplemente navegar al archivo desde el explorador de archivos
```
