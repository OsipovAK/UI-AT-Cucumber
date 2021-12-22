package ru.pepper.at.template.annotations;

import ru.pepper.at.template.Frames;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Page {
    String name();

    /*Указываем фрейм для элемента по умолчанию*/
    Frames frame() default Frames.TOP_WINDOW;

}
