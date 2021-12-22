package ru.pepper.at.template.stepdefs;


import com.codeborne.selenide.Condition;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.То;
import io.cucumber.java.ru.Тогда;
import org.aeonbits.owner.ConfigFactory;
import ru.pepper.at.template.Props;
import ru.pepper.at.template.TestContext;
public class EnrollmentsSteps {

    private static Props props = ConfigFactory.create(Props.class);
    private TestContext testContext = TestContext.getInstance();

    @Когда("выбрана {string} строчка таблицы заявок")
    public void demandsTableChooseFirst(String input) {
        testContext.getCurrentPage().getCollection("Все чекбоксы").get(Integer.parseInt(input) + 1).click();
    }

    @И("таблица заявок закончила загрузку")
    public void demandsTableLoaded() {
        testContext.getCurrentPage().getElement("Таблица заявок")
                .shouldNotBe(Condition.attributeMatching("class", ".*Table__withSkeleton.*"));
    }


    
}
