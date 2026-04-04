package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.ui.PatientFormUI;
import org.example.ui.VitalSignsFormUI;
import org.example.utils.Constants;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class RegisterPatient implements Task {

    private final String identificacion;
    private final String nombres;
    private final String apellidos;
    private final String fechaNacimiento;
    private final String genero;

    public RegisterPatient(String identificacion, String nombres, String apellidos,
                           String fechaNacimiento, String genero) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(Constants.REGISTER_PATIENT_URL),
                FillPatientForm.with(identificacion, nombres, apellidos, fechaNacimiento, genero),
                Click.on(PatientFormUI.REGISTER_BUTTON),
                WaitUntil.the(VitalSignsFormUI.FC_FIELD, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }

    public static RegisterPatient withData(String identificacion, String nombres, String apellidos,
                                           String fechaNacimiento, String genero) {
        return instrumented(RegisterPatient.class, identificacion, nombres, apellidos,
                fechaNacimiento, genero);
    }
}
