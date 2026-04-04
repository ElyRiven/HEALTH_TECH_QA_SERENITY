package org.example.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class TheCriticality implements Question<String> {

    private final String patientNombres;

    public TheCriticality(String patientNombres) {
        this.patientNombres = patientNombres;
    }

    @Override
    public String answeredBy(Actor actor) {
        String xpath = "//table[@data-slot='table']//tr[@data-slot='table-row']"
                + "[.//td[@data-slot='table-cell'][normalize-space()='" + patientNombres + "']]"
                + "//td[@data-slot='table-cell'][3]";
        return Text.of(Target.the("criticality of " + patientNombres).locatedBy(xpath))
                .answeredBy(actor);
    }

    public static TheCriticality ofPatient(String nombres) {
        return new TheCriticality(nombres);
    }
}
