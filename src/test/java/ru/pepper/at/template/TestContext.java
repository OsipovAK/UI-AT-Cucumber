package ru.pepper.at.template;

import com.codeborne.selenide.Selenide;
import com.google.common.collect.Maps;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pepper.at.template.annotations.Page;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class TestContext {
    private static final Logger LOG = LoggerFactory.getLogger(TestContext.class);

    private static final String PAGES_PACKAGE = "ru.pepper.at.template.pages";
    private static TestContext instance = new TestContext();
    private static Map<String, WebPage> pages;
    private ThreadLocal<WebPage> currentPage = new ThreadLocal<>();
    private static ThreadLocal<Scenario> scenario = new ThreadLocal<>();

    private TestContext() {
        initPages();
    }

    public static TestContext getInstance() {
        return instance;
    }

    public Map<String, WebPage> getPages() {
        return pages;
    }

    public WebPage getPage(String pageTitle) {
        WebPage p = pages.get(pageTitle);
        p.initialize();
        switchToFrame(p);
        return Selenide.page(p);
    }

    @SuppressWarnings("unchecked")
    private synchronized void initPages() {
        if (pages == null) {
            pages = Maps.newHashMap();
            new Reflections(PAGES_PACKAGE).getTypesAnnotatedWith(Page.class)
                    .stream()
                    .map(it -> {
                        if (WebPage.class.isAssignableFrom(it)) {
                            return (Class<? extends WebPage>) it;
                        } else {
                            throw new IllegalStateException(String.format("Класс %s должен наследоваться от WebPage", it.getName()));
                        }
                    }).forEach(clazz -> {
                try {
                    Constructor<? extends WebPage> constructor = clazz.getConstructor();
                    constructor.setAccessible(true);
                    WebPage p = constructor.newInstance();
                    pages.put(p.getTitle(), p);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalStateException("Ошибка при создании страницы");
                }
            });
        }
    }

    public WebPage getCurrentPage() {
        if (currentPage == null) throw new IllegalStateException("Текущая страница не задана");
        WebPage p = currentPage.get().initialize();
        switchToFrame(p);
        return Selenide.page(p);
    }

    public void setCurrentPage(WebPage currentPage) {
        this.currentPage.set(currentPage);
    }

    public void setCurrentPage(String pageName) {
        setCurrentPage(pages.get(pageName));
    }

    public String getPageUrl(String pageName) {
        return pages.get(pageName).getUrl();
    }

    public static Scenario getScenario() {
        return scenario.get();
    }

    public static void setScenario(Scenario scenario) {
        TestContext.scenario.set(scenario);
    }

    private void switchToFrame(WebPage p) {
        Selenide.switchTo().defaultContent();
        if (p.getFrame() != Frames.TOP_WINDOW) {
            try {
                Selenide.Wait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(p.getFrame().getXpath())));
                LOG.info("Switch to frame - " + p.getFrame());
            } catch (Exception e) {
                LOG.debug("Frame not found, the driver switched to the main frame");
            }
        }
    }
}
