package org.example.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.example.ui.VitalSignsFormUI;

public class TheVitalSignsError implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(VitalSignsFormUI.ERROR_MESSAGE).answeredBy(actor);
    }

    public static TheVitalSignsError inTheForm() {
        return new TheVitalSignsError();
    }
}
