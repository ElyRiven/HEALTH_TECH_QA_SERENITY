package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class VitalSignsFormUI {

    public static final Target FC_FIELD = Target.the("heart rate field")
            .located(By.id("frecuencia_cardiaca"));

    public static final Target FR_FIELD = Target.the("respiratory rate field")
            .located(By.id("frecuencia_respiratoria"));

    public static final Target SPO2_FIELD = Target.the("oxygen saturation field")
            .located(By.id("saturacion_o2"));

    public static final Target TEMP_FIELD = Target.the("temperature field")
            .located(By.id("temperatura"));

    public static final Target PRESION_FIELD = Target.the("blood pressure field")
            .located(By.id("presion"));

    public static final Target CONSCIOUSNESS_FIELD = Target.the("consciousness level field")
            .located(By.id("nivel_de_conciencia"));

    public static final Target PAIN_FIELD = Target.the("pain level field")
            .located(By.id("nivel_de_dolor"));

    public static final Target SAVE_BUTTON = Target.the("save vital signs button")
            .locatedBy("//button[@type='submit' and normalize-space()='Guardar']");

    public static final Target ERROR_MESSAGE = Target.the("vital signs error message")
            .located(By.cssSelector("div.text-red-600.text-xs"));
}
