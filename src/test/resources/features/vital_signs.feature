Feature: Vital Signs Registration
  As a medical staff member
  I want to register vital signs for a patient
  So that the system can automatically classify patient criticality

  Scenario: TC-004 - Successful vital signs registration with valid values
    Given the patient with id "1002003004", names "Carlos Alberto", surnames "Mendez Ruiz", birth date "03/15/1985" and gender "Hombre" is registered in the system
    When the medical staff fills the vital signs form with fc "80", fr "16", spo2 "98", temp "36.5", presion "120/80", consciousness "Alerta" and pain "2"
    And they click the "Guardar" button
    And they are redirected to the dashboard
    Then the system shows the alert message "Signos vitales registrados exitosamente"

  Scenario: TC-005 - Vital signs registration fails with out-of-range values
    Given the patient with id "1002003005", names "Maria Laura", surnames "Gomez Torres", birth date "07/20/1992" and gender "Mujer" is registered in the system
    When the medical staff fills the vital signs form with fc "80", fr "16", spo2 "5", temp "36.5", presion "120/80", consciousness "Alerta" and pain "2"
    And they click the "Guardar" button
    Then the vital signs form shows the validation error "Saturación O2: debe estar entre 50.0 y 100.0 %"
    And the medical staff remains on the vital signs form
