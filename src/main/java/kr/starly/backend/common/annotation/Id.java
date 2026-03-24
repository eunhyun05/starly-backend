package kr.starly.backend.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.data.annotation.Id
public @interface Id {

    String prefix() default "";

    int length() default 24;
}