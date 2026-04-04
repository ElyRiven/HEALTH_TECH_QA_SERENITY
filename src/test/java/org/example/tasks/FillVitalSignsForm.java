package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import org.example.ui.VitalSignsFormUI;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class FillVitalSignsForm implements Task {

    private final String frecuenciaCardiaca;
    private final String frecuenciaRespiratoria;
    private final String saturacionO2;
    private final String temperatura;
    private final String presion;
    private final String nivelConciencia;
    private final String nivelDolor;

    public FillVitalSignsForm(String frecuenciaCardiaca, String frecuenciaRespiratoria,
                               String saturacionO2, String temperatura, String presion,
                               String nivelConciencia, String nivelDolor) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.saturacionO2 = saturacionO2;
        this.temperatura = temperatura;
        this.presion = presion;
        this.nivelConciencia = nivelConciencia;
        this.nivelDolor = nivelDolor;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(frecuenciaCardiaca).into(VitalSignsFormUI.FC_FIELD),
                Enter.theValue(frecuenciaRespiratoria).into(VitalSignsFormUI.FR_FIELD),
                Enter.theValue(saturacionO2).into(VitalSignsFormUI.SPO2_FIELD),
                Enter.theValue(temperatura).into(VitalSignsFormUI.TEMP_FIELD),
                Enter.theValue(presion).into(VitalSignsFormUI.PRESION_FIELD),
                SelectFromOptions.byVisibleText(nivelConciencia).from(VitalSignsFormUI.CONSCIOUSNESS_FIELD),
                Enter.theValue(nivelDolor).into(VitalSignsFormUI.PAIN_FIELD)
        );
    }

    public static FillVitalSignsForm with(String frecuenciaCardiaca, String frecuenciaRespiratoria,
                                          String saturacionO2, String temperatura, String presion,
                                          String nivelConciencia, String nivelDolor) {
        return instrumented(FillVitalSignsForm.class, frecuenciaCardiaca, frecuenciaRespiratoria,
                saturacionO2, temperatura, presion, nivelConciencia, nivelDolor);
    }
}
