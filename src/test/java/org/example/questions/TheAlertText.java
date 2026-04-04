package org.example.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.DashboardUI;

public class TheAlertText implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(DashboardUI.ALERT_NOTIFICATION).answeredBy(actor);
    }

    public static TheAlertText inThePage() {
        return new TheAlertText();
    }
}
