package org.example.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.questions.TheCurrentUrl;
import org.example.questions.TheVitalSignsError;
import org.example.tasks.FillVitalSignsForm;
import org.example.tasks.RegisterPatient;
import org.example.ui.DashboardUI;
import org.example.ui.VitalSignsFormUI;
import org.example.utils.Constants;

import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class VitalSignsStepDefinitions {

    @Given("the patient with id {string}, names {string}, surnames {string}, birth date {string} and gender {string} is registered in the system")
    public void patientIsRegisteredInTheSystem(String id, String nombres, String apellidos,
                                               String birthDate, String gender) {
        OnStage.theActorCalled(Constants.ACTOR).wasAbleTo(
                RegisterPatient.withData(id, nombres, apellidos, birthDate, gender)
        );
    }

    @When("the medical staff fills the vital signs form with fc {string}, fr {string}, spo2 {string}, temp {string}, presion {string}, consciousness {string} and pain {string}")
    public void fillVitalSignsForm(String fc, String fr, String spo2, String temp,
                                   String presion, String consciousness, String pain) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                FillVitalSignsForm.with(fc, fr, spo2, temp, presion, consciousness, pain)
        );
    }

    @And("they click the \"Guardar\" button")
    public void clickGuardarButton() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Click.on(VitalSignsFormUI.SAVE_BUTTON)
        );
    }

    @Then("the vital signs form shows the validation error {string}")
    public void vitalSignsFormShowsValidationError(String expectedError) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(VitalSignsFormUI.ERROR_MESSAGE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                Ensure.that(TheVitalSignsError.inTheForm()).contains(expectedError)
        );
    }

    @And("they are redirected to the dashboard")
    public void theyAreRedirectedToTheDashboard() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(DashboardUI.DASHBOARD_TITLE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                Ensure.that(TheCurrentUrl.ofTheBrowser()).contains("/dashboard")
        );
    }

    @And("the medical staff remains on the vital signs form")
    public void medicalStaffRemainsOnVitalSignsForm() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheCurrentUrl.ofTheBrowser()).contains("/register/")
        );
    }
}
