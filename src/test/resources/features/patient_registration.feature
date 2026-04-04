Feature: Patient Registration
  As a medical staff member
  I want to register patients in the system
  So that I can track their health information

  Scenario: TC-001 - Successful patient registration with valid data
    Given the medical staff is on the patient registration form
    When they fill the registration form with id "1002003001", names "Andrea Cecilia", surnames "Tupiza Espinoza", birth date "05/21/1999" and gender "Mujer"
    And they click the "Registrar Paciente" button
    Then the system shows the alert message "Paciente con identificación 1002003001 registrado exitosamente"
    And the medical staff is redirected to the vital signs form for patient "1002003001"

  Scenario Outline: TC-002 - Registration fails when required fields are empty
    Given the medical staff is on the patient registration form
    When they fill the registration form leaving the "<field>" field empty
    And they click the "Registrar Paciente" button
    Then the system shows the alert message "<expectedError>"
    And the medical staff remains on the patient registration form

    Examples:
      | field               | expectedError                                                 |
      | identificacion      | Lo siento, debes escribir la identificación del paciente      |
      | nombres             | Lo siento, debes escribir los nombres del paciente            |
      | apellidos           | Lo siento, debes escribir los apellidos del paciente          |
      | fecha_de_nacimiento | Lo siento, debes escribir la fecha de nacimiento del paciente |

  Scenario: TC-003 - Registration fails when identification is duplicated
    Given a patient with id "1002003003" is already registered in the system
    And the medical staff is on the patient registration form
    When they fill the registration form with id "1002003003", names "Sofia Camila", surnames "Paz Chiriboga", birth date "09/09/1989" and gender "Mujer"
    And they click the "Registrar Paciente" button
    Then the system shows the alert message "Identificación duplicada"
    And the medical staff remains on the patient registration form
