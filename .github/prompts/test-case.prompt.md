# Caso de Prueba a automatizar

## Contexto

Necesito que implementes una automatización E2E de la interfaz de usuario en la URL `https://www.saucedemo.com/` que tiene el propósito de autenticar a los usuarios del sistema.
La interfaz de usuario requiere de 2 datos imprescindibles: usuario y contraseña.

El flujo correcto (happy path) de la interfaz es la siguiente:

- Ingresar un usuario válido en el campo "username", cuyo elemento HTML es un `input` con id = `user-name`.
- Ingresar la contraseña válida para dicho usuario en el campo "password", cuyo elemento HTML es un `input` con id = `password`.
- Seleccionar el botón de ingresar, cuyo elemento HTML es un `input` de tipo = `submit` y id = `login-button`.
- Cuando se verifica correctamente el usuario, el sistema redirige al usuario a la url `inventory.html`
- El sistema muestra los productos disponibles en la tienda, cuyo elemento HTML es un `div` con id = `inventory_container`.

El flujo incorrecto cuando se ingresa un usuario no registrado es el siguiente:

- Ingresar un usuario NO REGISTRADO en el campo "username".
- Ingresar una contraseña en el campo "password".
- Seleccionar el botón de ingresar.
- El sistema muestra un mensaje de error, cuyo elemento HTML es un `h3` con data-test = `error` y el texto = "Epic sadface: Username and password do not match any user in this service"
- El sistema mantiene al usuario en la url de inicio de sesión.

El flujo incorrecto cuando se ingresa una contraseña incorrecta es el siguiente:

- Ingresar un usuario válido en el campo "username".
- Ingresar una contraseña INCORRECTA en el campo "password".
- Seleccionar el botón de ingresar.
- El sistema muestra un mensaje de error, cuyo elemento HTML es un `h3` con data-test = `error` y el texto = "Epic sadface: Username and password do not match any user in this service"
- El sistema mantiene al usuario en la url de inicio de sesión.

## Datos de prueba

### Credenciales Válidas

- Usuarios válidos
  - standard_user
  - problem_user
  - error_user
  - visual_user

- Contraseña válida
  - secret_sauce

## Objetivo

Debes crear la automatización de estos escenarios mediante SerenityBDD con el patrón de diseño ScreenPlay, generando los escenarios, sus pasos en Gherkin haciendo uso de sus palabras clave (GIVEN, WHEN, THEN, AND, etc), sus tablas de datos de ser el caso e implementar el código necesario en los diferentes directorios del proyecto.
Finalmente debes asegurar que el proyecto permita la ejecución correcta de las pruebas y la generación del reporte HTML de SerenityBDD con los resultados de los escenarios y sus pasos de ejecución.
