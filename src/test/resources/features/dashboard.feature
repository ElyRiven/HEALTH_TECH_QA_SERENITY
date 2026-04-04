Feature: Dashboard and Triage Classification
  As a medical staff member
  I want to view patients and their criticality on the dashboard
  So that I can prioritize patient care

  Scenario Outline: Automatic criticality classification according to Manchester Protocol
    Given the patient with id "<id>", names "<nombres>", surnames "<apellidos>", birth date "<birthDate>" and gender "<gender>" is registered in the system
    And the medical staff fills the vital signs form with fc "<fc>", fr "<fr>", spo2 "<spo2>", temp "<temp>", presion "<presion>", consciousness "<consciousness>" and pain "<pain>"
    And they click the "Guardar" button
    Then they are redirected to the dashboard
    And the patient with names "<nombres>" has criticality "<criticality>" in the dashboard table

    Examples:
      | id         | nombres      | apellidos      | birthDate  | gender | fc  | fr | spo2 | temp | presion | consciousness | pain | criticality   |
      | 1002003006 | Pedro Luis   | Ramirez Vargas | 11/05/1975 | Hombre | 130 | 28 | 82   | 39.5 | 90/60   | Confuso       | 9    | Emergencia    |
      | 1002003007 | Ana Sofia    | Castro Mejia   | 04/22/1988 | Mujer  | 110 | 29 | 91   | 38.8 | 95/65   | Confuso       | 7    | Muy Urgente   |
      | 1002003008 | Jorge Andres | Silva Pena     | 08/10/1980 | Hombre | 95  | 19 | 95   | 38.8 | 110/75  | Alerta        | 5    | Urgente       |
      | 1002003009 | Laura Patri  | Moreno Diaz    | 02/14/1995 | Mujer  | 75  | 16 | 96   | 38.0 | 145/95  | Confuso       | 5    | Menos Urgente |
      | 1002003010 | Roberto Man  | Perez Jimenez  | 06/30/1970 | Hombre | 72  | 16 | 98   | 36.8 | 118/76  | Alerta        | 1    | No Urgente    |

  Scenario: TC-011 - Dashboard shows patients ordered by criticality descending
    Given the following patients are registered with their vital signs:
      | id         | nombres      | apellidos      | birthDate  | gender | fc | fr | spo2 | temp | presion | consciousness | pain |
      | 1002011005 | Carmen Sofia | Rojas Cano     | 07/04/1995 | Mujer  | 72 | 16 | 98.0 | 36.8 | 118/76  | Alerta        | 1    |
      | 1002011004 | Pablo Andres | Torres Mendez  | 11/30/1985 | Hombre | 75 | 16 | 96.0 | 38.0 | 145/95  | Confuso       | 5    |
      | 1002011003 | Elena Maria  | Duarte Sanchez | 08/10/1990 | Mujer  | 55 | 18 | 91.0 | 37.2 | 120/80  | Alerta        | 7    |
      | 1002011002 | Juan Carlos  | Vargas Herrera | 03/22/1975 | Hombre | 45 | 18 | 97.5 | 37.2 | 120/80  | Alerta        | 2    |
      | 1002011001 | Maria Luisa  | Paredes Rivera | 05/15/1980 | Mujer  | 35 | 18 | 97.5 | 37.2 | 120/80  | Alerta        | 2    |
    When the medical staff navigates to the dashboard
    Then the patients are displayed ordered from highest criticality to lowest

  Scenario: TC-013 - Visual notification appears when a new patient with vital signs is registered
    Given the medical staff navigates to the dashboard
    And the patient with id "1002003016", names "Valentina", surnames "Torres Suarez", birth date "09/25/2000" and gender "Mujer" is registered in the system
    And the medical staff fills the vital signs form with fc "72", fr "16", spo2 "98", temp "36.8", presion "118/76", consciousness "Alerta" and pain "1"
    And they click the "Guardar" button
    When they are redirected to the dashboard
    Then the dashboard shows a notification containing "Signos vitales registrados exitosamente"
    And the notification message contains "Paciente: Valentina Torres Suarez"
    And the notification message contains "Criticidad: No Urgente"
