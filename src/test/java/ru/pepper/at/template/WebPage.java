package ru.pepper.at.template;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.pepper.at.template.annotations.ElementName;
import ru.pepper.at.template.annotations.Page;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public abstract class WebPage {

    public abstract String getUrl();

    private Map<String, Object> namedElements;

    public String getTitle() {
        return this.getClass().getAnnotation(Page.class).name();
    }

    public Frames getFrame() {
        return this.getClass().getAnnotation(Page.class).frame();
    }

    /**
     * Получение элемента со страницы по имени (аннотированного "Name")
     */
    public SelenideElement getElement(String elementName) {
        return (SelenideElement) java.util.Optional.ofNullable(namedElements.get(elementName))
                .orElseThrow(() -> new IllegalArgumentException("Элемент " + elementName + " не описан на странице " + this.getClass().getName()));
    }

    /**
     * Получение элемента-списка со страницы по имени
     */
    public ElementsCollection getCollection(String listName) {
        Object value = namedElements.get(listName);
        if (!(value instanceof List)) {
            throw new IllegalArgumentException("Список " + listName + " не описан на странице " + this.getClass().getName());
        }
        return (ElementsCollection) value;
    }

    protected WebPage initialize() {
        if (namedElements == null) {
            namedElements = readNamedElements();
        }
        return this;
    }

    /**
     * Поиск и инициализации элементов страницы
     */
    private Map<String, Object> readNamedElements() {
        checkNamedAnnotations();
        return Arrays.stream(this.getClass().getFields())
                .filter(f -> f.isAnnotationPresent(ElementName.class))
                .collect(toMap(f -> f.getDeclaredAnnotation(ElementName.class).value(), this::extractFieldValueViaReflection));
    }

    /**
     * Поиск по аннотации "Name"
     */
    private void checkNamedAnnotations() {
        List<String> list = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(ElementName.class) != null)
                .map(f -> f.getDeclaredAnnotation(ElementName.class).value())
                .collect(toList());
        if (list.size() != new HashSet<>(list).size()) {
            throw new IllegalStateException("Найдено несколько аннотаций @Name с одинаковым значением в классе " + this.getClass().getName());
        }
    }

    private Object extractFieldValueViaReflection(Field field) {
        return extractFieldValue(field, this);
    }

    /**
     * Получение поля класса с помощью механизма рефлексии
     */
    private static Object extractFieldValue(Field field, Object owner) {
        field.setAccessible(true);
        try {
            return field.get(owner);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }
}
