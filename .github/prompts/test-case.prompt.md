# Casos de Prueba a automatizar

## Contexto

Necesito que implementes la automatización E2E de todos los casos de prueba que se especifican en el documento `TEST_CASES.md`. Utiliza la siguiente información para generar y ejecutar correctamente las pruebas:

### Rutas Principales

- BASE_URL: `http://localhost:5173`
- Dasboard: `/dashboard`
- Registro de Pacientes: `/register`
- Registro de Constantes Vitales: `/register/{pacientId}`

### Elementos de UI

**Dashboard Principal**

| Nombre                 | Elemento HTML |       Atributos       |        Texto         |
| ---------------------- | ------------- | :-------------------: | :------------------: |
| Título del Dashboard   | `h1`          |           -           | "Lista de pacientes" |
| Dashboard de Pacientes | `table`       | `data-slot` = "table" |          -           |
| Dashboard de Pacientes | `table`       | `data-slot` = "table" |          -           |
| Cuadro de Notificación | `div`         |   `role` = "alert"    |          -           |

**Formulario de Paciente**

| Nombre                    | Elemento HTML |                   Atributos                   |        Texto         |
| ------------------------- | ------------- | :-------------------------------------------: | :------------------: |
| Input Identificación      | `input`       |            `id` = "identificacion"            |          -           |
| Input Nombres             | `input`       |               `id` = "nombres"                |          -           |
| Input Apellidos           | `input`       |              `id` = "apellidos"               |          -           |
| Input Fecha de Nacimiento | `input`       | `id` = "fecha_de_nacimiento", `type` = "date" |          -           |
| Input Genero              | `select`      |                `id` = "genero"                |          -           |
| Cuadro de Notificación    | `div`         |               `role` = "alert"                |          -           |
| Botón Registrar Paciente  | `button`      |               `type` = "submit"               | "Registrar Paciente" |

**Formulario de Signos Vitales**

| Nombre                        | Elemento HTML |            Atributos             |   Texto   |
| ----------------------------- | ------------- | :------------------------------: | :-------: |
| Input Frecuencia Cardiaca     | `input`       |   `id` = "frecuencia_cardiaca"   |     -     |
| Input Frecuencia Respiratoria | `input`       | `id` = "frecuencia_respiratoria" |     -     |
| Input Saturación O2           | `input`       |      `id` = "saturacion_o2"      |     -     |
| Input Temperatura             | `input`       |       `id` = "temperatura"       |     -     |
| Input Presión                 | `input`       |         `id` = "presion"         |     -     |
| Input Nivel de Consciencia    | `select`      |   `id` = "nivel_de_conciencia"   |     -     |
| Input Nivel de dolor          | `input`       |     `id` = "nivel_de_dolor"      |     -     |
| Cuadro de Notificación        | `div`         |         `role` = "alert"         |     -     |
| Botón Guardar Signos Vitales  | `button`      |        `type` = "submit"         | "Guardar" |

### Datos de prueba

Define los datos de prueba requeridos para cada caso de prueba con los definidos en los documentos `TEST_CASES.md`, `TRIAGE_TEST_CASES.md` y según los rangos definidos en el documento `MANCHESTER_PROTOCOL.md` y datos ficticios para los pacientes que no estén definidos en el documento de casos de prueba. Ten en cuenta que el formato de entrada para la fecha de nacimiento es mm/dd/yyyy y el input es de tipo date y para el género, existen 3 valores posibles: "Hombre", "Mujer" y "Otro", disponibles mediante el select del campo.

### Textos a comprobar

**Dashboard**

- Notificación de registro de un nuevo paciente:
  "Signos vitales registrados exitosamente
  Paciente: [nombres] [apellidos]
  Criticidad: [criticidad asignada del paciente]"

**Formulario de Paciente**

- Éxito al registrar un paciente: "Paciente con identificación {pacientId} registrado exitosamente".
- Error por campos obligatorios vacíos: "Lo siento, debes [campo faltante]".
  - identificación faltante: "escribir la identificación del paciente"
  - nombres faltante: "escribir los nombres del paciente"
  - apellidos faltante: "escribir los apellidos del paciente"
  - fecha de nacimiento faltante: "escribir la fecha de nacimiento del paciente"
  - género faltante: "seleccionar el género del paciente"
- Error por identificación duplicada: "Identificación duplicada"

**Formulario de Signos Vitales**

- Éxito al registrar signos vitales: "Signos vitales registrados exitosamente".
- Errores en los campos del formulario:
  - Frecuencia Cardiaca:
    - Campo faltante: "Frecuencia cardíaca: campo requerido"
    - Valor fuera de rango: "Frecuencia cardíaca: debe estar entre 20 y 300 bpm"
  - Frecuencia Respiratoria:
    - Campo faltante: "Frecuencia respiratoria: campo requerido"
    - Valor fuera de rango: "Frecuencia respiratoria: debe estar entre 1 y 60 rpm"
  - Saturación O2:
    - Campo faltante: "Saturación O2: campo requerido"
    - Valor fuera de rango: "Saturación O2: debe estar entre 50.0 y 100.0 %"
  - Temperatura:
    - Campo faltante: "Temperatura: campo requerido"
    - Valor fuera de rango: "Temperatura: debe estar entre 25.0 y 45.0 °C"
  - Presión:
    - Campo faltante: "Presión arterial: campo requerido"
    - Valor sistólica fuera de rango: "Presión arterial: la sistólica debe estar entre 50 y 300 mmHg"
    - Valor diastólica fuera de rango: "Presión arterial: la diastólica debe estar entre 20 y 200 mmHg"
    - Valor inválido: "Presión arterial: la diastólica debe ser menor que la sistólica"
    - Formato inválido: "Presión arterial: formato inválido. Use 'sistólica/diastólica' con 2-3 dígitos (ej: 120/80)"
  - Nivel de dolor:
    - Campo faltante: "Nivel de dolor: campo requerido"
    - Valor fuera de rango: "Nivel de dolor: debe estar entre 0 y 10 (escala EVA)"

## Objetivo

Debes crear la automatización de estos escenarios mediante SerenityBDD con el patrón de diseño ScreenPlay, generando los escenarios, sus pasos en Gherkin haciendo uso de sus palabras clave (GIVEN, WHEN, THEN, AND, etc), sus tablas de datos de ser el caso e implementar el código necesario en los diferentes directorios del proyecto.
Finalmente debes asegurar que el proyecto permita la ejecución correcta de las pruebas y la generación del reporte HTML de SerenityBDD con los resultados de los escenarios y sus pasos de ejecución.
