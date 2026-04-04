package org.example.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.hooks.OpenBrowser;
import org.example.questions.TheAlertText;
import org.example.questions.TheCriticality;
import org.example.questions.TheCriticalityOrder;
import org.example.tasks.RegisterPatient;
import org.example.tasks.RegisterVitalSigns;
import org.example.ui.DashboardUI;
import org.example.utils.Constants;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.assertj.core.api.Assertions.assertThat;

public class DashboardStepDefinitions {

    @Given("the medical staff navigates to the dashboard")
    public void medicalStaffNavigatesToTheDashboard() {
        OnStage.theActorCalled(Constants.ACTOR).wasAbleTo(
                OpenBrowser.inTheUrl(Constants.DASHBOARD_URL)
        );
    }

    @Given("the following patients are registered with their vital signs:")
    public void registerPatientsWithVitalSigns(DataTable dataTable) {
        List<Map<String, String>> patients = dataTable.asMaps();
        for (Map<String, String> patient : patients) {
            OnStage.theActorCalled(Constants.ACTOR).attemptsTo(
                    RegisterPatient.withData(
                            patient.get("id"),
                            patient.get("nombres"),
                            patient.get("apellidos"),
                            patient.get("birthDate"),
                            patient.get("gender")
                    )
            );
            OnStage.theActorCalled(Constants.ACTOR).attemptsTo(
                    RegisterVitalSigns.withData(
                            patient.get("fc"),
                            patient.get("fr"),
                            patient.get("spo2"),
                            patient.get("temp"),
                            patient.get("presion"),
                            patient.get("consciousness"),
                            patient.get("pain")
                    )
            );
            OnStage.theActorCalled(Constants.ACTOR).attemptsTo(
                    WaitUntil.the(DashboardUI.DASHBOARD_TITLE, isVisible())
                            .forNoMoreThan(Duration.ofSeconds(10))
            );
        }
    }

    @Then("the patient with names {string} has criticality {string} in the dashboard table")
    public void patientHasCriticalityInDashboard(String nombres, String expectedCriticality) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(DashboardUI.PATIENTS_TABLE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheCriticality.ofPatient(nombres)).isEqualTo(expectedCriticality)
        );
    }

    @Then("the patients are displayed ordered from highest criticality to lowest")
    public void patientsAreDisplayedOrderedByDashboard() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(DashboardUI.PATIENTS_TABLE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
        List<String> criticalities = OnStage.theActorInTheSpotlight()
                .asksFor(TheCriticalityOrder.inTheDashboard());
        List<String> criticalityOrder = List.of(
                "Emergencia", "Muy Urgente", "Urgente", "Menos Urgente", "No Urgente"
        );
        int lastIndex = -1;
        for (String criticality : criticalities) {
            int currentIndex = criticalityOrder.indexOf(criticality);
            if (currentIndex >= 0) {
                assertThat(currentIndex)
                        .as("Criticality '%s' should appear after previous criticality level", criticality)
                        .isGreaterThanOrEqualTo(lastIndex);
                lastIndex = currentIndex;
            }
        }
    }

    @Then("the dashboard shows the message {string}")
    public void dashboardShowsMessage(String expectedMessage) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(DashboardUI.EMPTY_DASHBOARD_MESSAGE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }

    @Then("the dashboard shows a notification containing {string}")
    public void dashboardShowsNotificationContaining(String expectedText) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(DashboardUI.ALERT_NOTIFICATION, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                Ensure.that(TheAlertText.inThePage()).contains(expectedText)
        );
    }

    @And("the notification message contains {string}")
    public void notificationMessageContains(String expectedText) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheAlertText.inThePage()).contains(expectedText)
        );
    }
}
