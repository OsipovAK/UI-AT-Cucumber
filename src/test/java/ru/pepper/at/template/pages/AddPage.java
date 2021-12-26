package ru.pepper.at.template.pages;

import com.codeborne.selenide.SelenideElement;
import ru.pepper.at.template.WebPage;
import ru.pepper.at.template.annotations.ElementName;
import ru.pepper.at.template.annotations.Page;

import static com.codeborne.selenide.Selenide.$x;
import static ru.pepper.at.template.stepdefs.CommonSteps.props;


@Page(name = "Добавить скидку")
public class AddPage extends Main {
    @Override
    public String getUrl() {
        return props.pepperUrl() + "/submission/deals/add";
    }



//    @ElementName("кнопка Меню")
//    public SelenideElement buttonMenu = $x("//a[@aria-haspopup='true']");
//
//    @ElementName("кнопка Промокоды")
//    public SelenideElement buttonPromo = $x("//a[contains(text(),'Промокоды')]");
//
//    @ElementName("кнопка Фильтры")
//    public SelenideElement buttonFilter = $x("//span[contains(@class,'cept-filter-popover-trigger')]");
//
//    @ElementName("кнопка В виде текста")
//    public SelenideElement buttonTextView = $x("//button[@title='Показывать скидки в виде текста']//span");
//
//    @ElementName("блок Описание")
//    public SelenideElement divDesc = $x("//div[contains(@data-handler,'lightbox-xhr emoticon-preview')]");
//
//    @ElementName("импут Поиска")
//    public SelenideElement inputSeach = $x("//input[@name='q']");
//
//    @ElementName("кнопка Все элементы")
//    public SelenideElement fullResults = $x("//button[contains(text(),'Показать все результаты')]");
//
//    @ElementName("бар быстрой навигации")
//    public SelenideElement navBar = $x("//div[@class='groupPromo--bg']");
//
//    @ElementName("кнопка Авторизации")
//    public SelenideElement buttonLogin = $x("//span[contains(text(),'Зарегистрироваться / Войти')]");
//
//    @ElementName("инпут Логин")
//    public SelenideElement inputLogin = $x("//input[@id='loginModalForm-identity']");
//
//    @ElementName("инпут Пароль")
//    public SelenideElement inputPass = $x("//input[@id='loginModalForm-password']");
//
//    @ElementName("кнопка Войти")
//    public SelenideElement buttonLoginBlue = $x("//button[@name='form_submit']");
//
//    @ElementName("сообщение об ошибке авторизации")
//    public SelenideElement testLoginError = $x("//p[@class='formList-info formList-info--error']");
//





}
