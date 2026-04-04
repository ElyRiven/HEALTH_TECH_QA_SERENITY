package org.example.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.hooks.OpenBrowser;
import org.example.questions.TheAlertText;
import org.example.questions.TheCurrentUrl;
import org.example.tasks.FillPatientForm;
import org.example.tasks.RegisterPatient;
import org.example.ui.DashboardUI;
import org.example.ui.PatientFormUI;
import org.example.ui.VitalSignsFormUI;
import org.example.utils.Constants;

import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class PatientRegistrationStepDefinitions {

    @Given("the medical staff is on the patient registration form")
    public void medicalStaffIsOnPatientRegistrationForm() {
        OnStage.theActorCalled(Constants.ACTOR).wasAbleTo(
                OpenBrowser.inTheUrl(Constants.REGISTER_PATIENT_URL)
        );
    }

    @Given("a patient with id {string} is already registered in the system")
    public void patientIsAlreadyRegisteredInTheSystem(String patientId) {
        OnStage.theActorCalled(Constants.ACTOR).attemptsTo(
                OpenBrowser.inTheUrl(Constants.REGISTER_PATIENT_URL),
                FillPatientForm.with(patientId, "Sofia Camila", "Paz Chiriboga", "09/09/1989", "Mujer"),
                Click.on(PatientFormUI.REGISTER_BUTTON),
                WaitUntil.the(VitalSignsFormUI.FC_FIELD, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }

    @When("they fill the registration form with id {string}, names {string}, surnames {string}, birth date {string} and gender {string}")
    public void fillRegistrationForm(String id, String nombres, String apellidos,
                                     String birthDate, String gender) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                FillPatientForm.with(id, nombres, apellidos, birthDate, gender)
        );
    }

    @When("they fill the registration form leaving the {string} field empty")
    public void fillRegistrationFormWithMissingField(String field) {
        String id = field.equals("identificacion") ? "" : "9999000099";
        String nombres = field.equals("nombres") ? "" : "Luis Andres";
        String apellidos = field.equals("apellidos") ? "" : "Caceres Estrada";
        String birthDate = field.equals("fecha_de_nacimiento") ? "" : "01/08/1983";
        OnStage.theActorInTheSpotlight().attemptsTo(
                FillPatientForm.with(id, nombres, apellidos, birthDate, "Hombre")
        );
    }

    @And("they click the \"Registrar Paciente\" button")
    public void clickRegistrarPacienteButton() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Click.on(PatientFormUI.REGISTER_BUTTON)
        );
    }

    @Then("the system shows the alert message {string}")
    public void systemShowsAlertMessage(String expectedMessage) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(DashboardUI.ALERT_NOTIFICATION, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                Ensure.that(TheAlertText.inThePage()).contains(expectedMessage)
        );
    }

    @And("the medical staff is redirected to the vital signs form for patient {string}")
    public void medicalStaffIsRedirectedToVitalSignsForm(String patientId) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheCurrentUrl.ofTheBrowser()).contains("/register/" + patientId)
        );
    }

    @And("the medical staff remains on the patient registration form")
    public void medicalStaffRemainsOnPatientRegistrationForm() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheCurrentUrl.ofTheBrowser()).contains("/register")
        );
    }
}
