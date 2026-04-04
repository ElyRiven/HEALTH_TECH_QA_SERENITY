package org.example.stepdefinitions.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.example.utils.DatabaseCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Hook {

    private static final Logger log = LoggerFactory.getLogger(Hook.class);

    @Before
    public void startScenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @After
    public void cleanDatabase() {
        try {
            DatabaseCleaner.cleanAll();
        } catch (SQLException e) {
            log.warn("Could not clean database after scenario: {}", e.getMessage());
        }
    }
}
