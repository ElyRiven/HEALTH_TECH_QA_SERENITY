package org.example.stepdefinitions.Hooks;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class Hook {

    @Before
    public void startScenario() {
        OnStage.setTheStage(new OnlineCast());
    }
}
