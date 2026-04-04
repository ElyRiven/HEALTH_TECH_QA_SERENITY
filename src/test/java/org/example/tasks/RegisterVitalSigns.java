package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import org.example.ui.VitalSignsFormUI;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegisterVitalSigns implements Task {

    private final String frecuenciaCardiaca;
    private final String frecuenciaRespiratoria;
    private final String saturacionO2;
    private final String temperatura;
    private final String presion;
    private final String nivelConciencia;
    private final String nivelDolor;

    public RegisterVitalSigns(String frecuenciaCardiaca, String frecuenciaRespiratoria,
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
                FillVitalSignsForm.with(frecuenciaCardiaca, frecuenciaRespiratoria,
                        saturacionO2, temperatura, presion, nivelConciencia, nivelDolor),
                Click.on(VitalSignsFormUI.SAVE_BUTTON)
        );
    }

    public static RegisterVitalSigns withData(String frecuenciaCardiaca, String frecuenciaRespiratoria,
                                              String saturacionO2, String temperatura, String presion,
                                              String nivelConciencia, String nivelDolor) {
        return instrumented(RegisterVitalSigns.class, frecuenciaCardiaca, frecuenciaRespiratoria,
                saturacionO2, temperatura, presion, nivelConciencia, nivelDolor);
    }
}
