Feature: Empty Dashboard State
  As a medical staff member
  I want to see an appropriate message when no patients are registered
  So that I know the system is functioning but has no current patients

  Scenario: TC-012 - Dashboard shows empty message when no patients are registered
    Given the medical staff navigates to the dashboard
    Then the dashboard shows the message "No hay pacientes en espera"
