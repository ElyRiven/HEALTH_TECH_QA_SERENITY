package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import org.example.ui.PatientFormUI;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class FillPatientForm implements Task {

    private final String identificacion;
    private final String nombres;
    private final String apellidos;
    private final String fechaNacimiento;
    private final String genero;

    public FillPatientForm(String identificacion, String nombres, String apellidos,
                           String fechaNacimiento, String genero) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (!identificacion.isEmpty()) {
            actor.attemptsTo(Enter.theValue(identificacion).into(PatientFormUI.ID_FIELD));
        }
        if (!nombres.isEmpty()) {
            actor.attemptsTo(Enter.theValue(nombres).into(PatientFormUI.NOMBRES_FIELD));
        }
        if (!apellidos.isEmpty()) {
            actor.attemptsTo(Enter.theValue(apellidos).into(PatientFormUI.APELLIDOS_FIELD));
        }
        if (!fechaNacimiento.isEmpty()) {
            actor.attemptsTo(Enter.theValue(fechaNacimiento).into(PatientFormUI.BIRTH_DATE_FIELD));
        }
        if (!genero.isEmpty()) {
            actor.attemptsTo(SelectFromOptions.byVisibleText(genero).from(PatientFormUI.GENDER_FIELD));
        }
    }

    public static FillPatientForm with(String identificacion, String nombres, String apellidos,
                                       String fechaNacimiento, String genero) {
        return instrumented(FillPatientForm.class, identificacion, nombres, apellidos,
                fechaNacimiento, genero);
    }
}
