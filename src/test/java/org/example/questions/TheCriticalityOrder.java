package org.example.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class TheCriticalityOrder implements Question<List<String>> {

    @Override
    public List<String> answeredBy(Actor actor) {
        List<WebElement> cells = BrowseTheWeb.as(actor).getDriver()
                .findElements(By.xpath(
                        "//table[@data-slot='table']//tr[@data-slot='table-row']"
                                + "//td[@data-slot='table-cell'][3]"
                ));
        return cells.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public static TheCriticalityOrder inTheDashboard() {
        return new TheCriticalityOrder();
    }
}
