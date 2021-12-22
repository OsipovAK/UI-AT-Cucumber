package ru.pepper.at.template;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.cucumber.java.Before;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger LOG = LoggerFactory.getLogger(Hooks.class);
    private static Props props = ConfigFactory.create(Props.class);

    @Before(order = 1)
    public void setup() {
        boolean isMac = System.getProperty("os.name").contains("Mac OS");
        if (!StringUtils.isBlank(props.remoteUrl())) {
            LOG.info("Tests will be running in Selenoid " + props.remoteUrl());
            Configuration.remote = props.remoteUrl();
        } else {
            if(isMac){
                System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/chromedriver");
            } else {
                System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/chromedriver.exe");
            }
        }
        Configuration.browser="chrome";
        if(isMac){
            Configuration.browserBinary="/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
        } else {
            Configuration.browserBinary="C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
        }
        Configuration.startMaximized = true;
        Configuration.timeout = props.timeout();
        Configuration.reportsFolder = "target/allure-results";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }
}
