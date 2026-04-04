package org.example.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class DashboardUI {

    public static final Target DASHBOARD_TITLE = Target.the("dashboard title")
            .locatedBy("//h1[contains(text(), 'Lista de pacientes')]");

    public static final Target PATIENTS_TABLE = Target.the("patients table")
            .locatedBy("//table[@data-slot='table']");

    public static final Target ALERT_NOTIFICATION = Target.the("alert notification")
            .locatedBy("//div[@role='alert']");

    public static final Target EMPTY_DASHBOARD_MESSAGE = Target.the("empty dashboard message")
            .locatedBy("//p[contains(., 'No hay pacientes en espera')]");
}
