# Reglas de formato para Serenity Screenplay

Este documento define ejemplos de la sintaxis a usar en los diferentes archivos Java dentro de los directorios del proyecto de Serenity con el patrón Screenplay.

## `src/test/java/org/example/`

### `hooks/`

Estos archivos deben configurar el contexto necesario para los escenarios de la automatización.

```java
package org.example.hooks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class OpenBrowser implements Task {

    private final String url;

    public OpenBrowser(String url) {
        this.url = url;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(url)
        );
    }

    public static OpenBrowser inTheUrl(String url) {
        return instrumented(OpenBrowser.class, url);
    }
}
```

### `questions/`

Estos archivos definen preguntas que el actor puede hacer para obtener información del sistema y generar aserciones de su comportamiento.

```java
package org.example.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class TheCurrentUrl implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
    }

    public static TheCurrentUrl ofTheBrowser() {
        return new TheCurrentUrl();
    }
}
```

### `runners/`

Estos archivos definen los suite runners mediante el uso de JUnit Platform + Cucumber engine.

```java
package org.example.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "io.cucumber.core.plugin.SerenityReporter"
)
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "org.example.stepdefinitions"
)
public class TestRunnerUserRegister {
}
```

### `stepdefinitions/`

Estos archivos definen la conección del escenario y los pasos Gherkin con las tareas del actor.

```java
package org.example.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;

import org.example.hooks.OpenBrowser;
import org.example.questions.TheCurrentUrl;
import org.example.questions.TheVisibilityOfTheErrorMessage;
import org.example.tasks.RegisterUser;
import org.example.utils.Constants;

import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotCurrentlyVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.example.ui.RegistrationPageUI.BUTTON_CREATE_ACCOUNT;
import static org.example.ui.RegistrationPageUI.ERROR_MESSAGE;

public class UserRegistrationStepDefinitions {

    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;

    @Given("the user is on the registration page")
    public void userIsOnTheRegistrationPage() {
        OnStage.theActorCalled(Constants.ACTOR).wasAbleTo(
                OpenBrowser.inTheUrl(Constants.URL_REGISTRATION)
        );
    }

    @When("the user enters {string} in the name field")
    public void userEntersInTheNameField(String fullName) {
        this.fullName = fullName;
    }

    @And("the user enters {string} in the email field")
    public void userEntersInTheEmailField(String email) {
        this.email = email;
    }

    @And("the user enters {string} in the password field")
    public void userEntersInThePasswordField(String password) {
        this.password = password;
    }

    @And("the user enters {string} in the confirm password field")
    public void userEntersInTheConfirmPasswordField(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @And("clicks the \"Create Account\" button")
    public void clicksTheCreateAccountButton() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                RegisterUser.withData(fullName, email, password, confirmPassword)
        );
    }

    @Then("the account is created through Firebase Authentication")
    public void accountIsCreatedThroughFirebaseAuthentication() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(BUTTON_CREATE_ACCOUNT, isNotCurrentlyVisible()).forNoMoreThan(Duration.ofSeconds(10))
        );
    }

    @And("the user is redirected to the dashboard")
    public void userIsRedirectedToTheDashboard() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheCurrentUrl.ofTheBrowser()).contains("/dashboard")
        );
    }

    @Then("an error message is displayed in the form")
    public void errorMessageIsDisplayedInTheForm() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(ERROR_MESSAGE, isVisible()).forNoMoreThan(Duration.ofSeconds(10)),
                Ensure.that(TheVisibilityOfTheErrorMessage.inTheForm()).isTrue()
        );
    }

    @And("the user remains on the registration page")
    public void userRemainsOnTheRegistrationPage() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheCurrentUrl.ofTheBrowser()).contains("/register")
        );
    }
}
```

#### `stepdefinitions/Hooks/`

Hooks de inicialización de escenarios.

```java
package org.example.stepdefinitions.Hooks;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class Hook {

    @Before
    public void startScenario() {
        OnStage.setTheStage(new OnlineCast());
    }
}
```

### `tasks/`

Estos archivos definen las interacciones de alto nivel que el actor puede realizar.

```java
package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.example.ui.RegistrationPageUI;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegisterUser implements Task {

    private final String fullName;
    private final String email;
    private final String password;
    private final String confirmPassword;

    public RegisterUser(String fullName, String email, String password, String confirmPassword) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(fullName).into(RegistrationPageUI.FULL_NAME_FIELD),
                Enter.theValue(email).into(RegistrationPageUI.EMAIL_FIELD),
                Enter.theValue(password).into(RegistrationPageUI.PASSWORD_FIELD),
                Enter.theValue(confirmPassword).into(RegistrationPageUI.CONFIRM_PASSWORD_FIELD),
                Click.on(RegistrationPageUI.BUTTON_CREATE_ACCOUNT)
        );
    }

    public static RegisterUser withData(String fullName, String email, String password, String confirmPassword) {
        return instrumented(RegisterUser.class, fullName, email, password, confirmPassword);
    }
}
```

### `ui/`

Estos archivos definen los elementos de la interfaz de usuario (Targets) con los que la automatización va a interactuar.

```java
package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PaginaRegistroUI {

    public static final Target FULL_NAME_FIELD = Target.the("full name field")
            .located(By.id("displayName"));

    public static final Target EMAIL_FIELD = Target.the("email field")
            .located(By.id("email"));

    public static final Target PASSWORD_FIELD = Target.the("password field")
            .located(By.id("password"));

    public static final Target CONFIRM_PASSWORD_FIELD = Target.the("confirm password field")
            .located(By.id("confirmPassword"));

    public static final Target BUTTON_CREATE_ACCOUNT = Target.the("button create account")
            .locatedBy("//button[@type='submit' and text()='Crear Cuenta']");

    public static final Target ERROR_MESSAGE = Target.the("error message")
            .locatedBy("//div[text() = 'El correo electrónico ya está en uso']");
}

```

### `utils/`

Estos archivos definen utilidades y constantes globales a utilizar en todo el proyecto

```java
package org.example.utils;

public final class Constants {

    private Constants() {}

    public static final String BASE_URL              = "http://localhost:3000";
    public static final String REGISTER_URL          = BASE_URL + "/register";
    public static final String DASHBOARD_URL         = BASE_URL + "/dashboard";

    public static final String ACTOR                 = "Elizabeth";
    public static final String EMAIL_IN_USE_MESSAGE = "El correo electrónico ya está en uso";
}

```

## `src/test/resources/`

### `features/`

Estos archivos definen los escenarios de la automatización en lenguaje Gherkin (.feature) en inglés y sus respectivas tablas de datos de ser necesario.

```gherkin
Feature: User Registration

  Scenario Outline: Successful registration with valid credentials
    Given the user is on the registration page
    When the user enters "<fullName>" in the name field
    And the user enters "<email>" in the email field
    And the user enters "<password>" in the password field
    And the user enters "<confirmPassword>" in the confirm password field
    And clicks the "Create Account" button
    Then the account is created through Firebase Authentication
    And the user is redirected to the dashboard

    Examples:
      | fullName           | email                      | password    | confirmPassword |
      | María Elena Garcés | maria.garces@correo.com    | Passw0rd!   | Passw0rd!       |
      | William Estrada    | william.estrada@correo.com | S3gur0#2026 | S3gur0#2026     |
      | Emma Ortega        | emma.ortega@correo.com     | T3stUs3r@   | T3stUs3r@       |

  Scenario Outline: Registration fails when the email is already registered
    Given the user is on the registration page
    When the user enters "<fullName>" in the name field
    And the user enters "<email>" in the email field
    And the user enters "<password>" in the password field
    And the user enters "<confirmPassword>" in the confirm password field
    And clicks the "Create Account" button
    Then an error message is displayed in the form
    And the user remains on the registration page

    Examples:
      | fullName    | email                   | password  | confirmPassword |
      | Used Email  | correo.usado@correo.com | Passw0rd! | Passw0rd!       |
```
