package ru.pepper.at.template.stepdefs;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.То;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import ru.pepper.at.template.Props;
import ru.pepper.at.template.TestContext;

import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.open;

public class CommonSteps {

    public static Props props = ConfigFactory.create(Props.class);
    private TestContext testContext = TestContext.getInstance();

    @Дано("открытый браузер со страницей {string}")
    public void openPepperMainPage(String pageName) {
        open(props.pepperUrl());
        testContext.setCurrentPage(pageName);
    }

    @Когда("пользователь нажимает на {string}")
    public void clickOn(String elementName) {
        testContext.getCurrentPage().getElement(elementName)
                .shouldBe(Condition.exist, Condition.visible)
                .click();
    }

    @Когда("пользователь вводит в поле {string} текст {string}")
    public void clickOn(String elementName, String input) {
        testContext.getCurrentPage().getElement(elementName)
                .shouldBe(Condition.exist, Condition.visible)
                .doubleClick()
                .sendKeys(input);
    }

    @Когда("пользователь выбирает значение {string} из списка {string}")
    public void selectValueFromList(String value, String listName) {
        testContext.getCurrentPage().getCollection(listName)
                .findBy(Condition.exactText(value))
                .shouldBe(Condition.exist, Condition.visible)
                .click();
    }

    @То("открывается страница {string}")
    public void pageOpening(String pageName) {
        Assert.assertEquals("Проверка URL " + pageName,
                testContext.getPageUrl(pageName), WebDriverRunner.url());
        testContext.setCurrentPage(pageName);
    }

    @То("открывается страница {string} подходящая под {string}")
    public void pageOpening2(String pageName, String expectedRegex) {
        boolean result = Pattern.compile(expectedRegex).matcher(WebDriverRunner.url()).find();
        Assert.assertTrue("Проверка URL " + expectedRegex,
                result);
        testContext.setCurrentPage(pageName);
    }

    @То("присутствует элемент {string}")
    public void checkElementExist(String elementName)  {
        testContext.getCurrentPage().getElement(elementName).shouldBe(Condition.exist, Condition.visible);
    }

    @То("присутствуют все элементы")
    public void checkGivenElementsExist(DataTable elementNames)  {
        elementNames.asList().forEach(o ->
                testContext.getCurrentPage().getElement(o)
                        .shouldBe(Condition.exist, Condition.visible));
    }

    @То("отсутствует элемент {string}")
    public void checkElementNotExist(String elementName)  {
        testContext.getCurrentPage().getElement(elementName).shouldNotBe(Condition.exist, Condition.visible);
    }

    @То("текст элемента {string} равен {string}")
    public void checkElementText(String elementName, String expectedText)  {
        testContext.getCurrentPage().getElement(elementName).shouldBe(Condition.exactText(expectedText));
    }

    @То("текст элемента {string} подходит под {string}")
    public void checkElementRegex(String elementName, String expectedRegex)  {
        testContext.getCurrentPage().getElement(elementName).shouldBe(Condition.matchText(expectedRegex));
    }

    @То("в списке {string} присутсвуют значения")
    public void checkListValues(String listName, DataTable values)  {
        ElementsCollection listElement = testContext.getCurrentPage().getCollection(listName);
        values.asList().forEach(
                o -> listElement.findBy(Condition.exactText(o)).shouldBe(Condition.exist, Condition.visible));
    }

    @То("элемент {string} активен")
    public void checkElementActive(String elementName)  {
        testContext.getCurrentPage().getElement(elementName)
                .shouldBe(Condition.attributeMatching("class", ".*active.*"));
    }

    @То("элемент {string} не активен")
    public void checkElementNotActive(String elementName)  {
        testContext.getCurrentPage().getElement(elementName)
                .shouldNotBe(Condition.attributeMatching("class", ".*active.*"));
    }

    @То("все элементы {string} активны")
    public void checkAllElementsActive(String elementName)  {
        testContext.getCurrentPage().getCollection(elementName)
                .forEach(o -> o.shouldBe(Condition.attributeMatching("class", ".*active.*")));
    }

    @То("все элементы {string} не активны")
    public void checkAllElementsNotActive(String elementName)  {
        testContext.getCurrentPage().getCollection(elementName)
                .forEach(o -> o.shouldNotBe(Condition.attributeMatching("class", ".*active.*")));
    }

    @То("все элементы активны")
    public void checkGivenElementsActive(DataTable elementNames)  {
        elementNames.asList().forEach(o ->
                testContext.getCurrentPage().getElement(o)
                    .shouldBe(Condition.attributeMatching("class", ".*active.*")));
    }
}
