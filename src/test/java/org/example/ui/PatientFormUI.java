package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PatientFormUI {

    public static final Target ID_FIELD = Target.the("identification field")
            .located(By.id("identificacion"));

    public static final Target NOMBRES_FIELD = Target.the("names field")
            .located(By.id("nombres"));

    public static final Target APELLIDOS_FIELD = Target.the("surnames field")
            .located(By.id("apellidos"));

    public static final Target BIRTH_DATE_FIELD = Target.the("birth date field")
            .located(By.id("fecha_de_nacimiento"));

    public static final Target GENDER_FIELD = Target.the("gender field")
            .located(By.id("genero"));

    public static final Target REGISTER_BUTTON = Target.the("register patient button")
            .locatedBy("//button[@type='submit' and normalize-space()='Registrar Paciente']");
}
