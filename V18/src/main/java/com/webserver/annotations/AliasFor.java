package com.webserver.annotations;

import java.lang.annotation.*;

/**
 * @author sytsnb@gmail.com
 * @Date 2022 2022/8/19 15:08
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AliasFor {
    @AliasFor("attribute")
    String value() default "";

    @AliasFor("value")
    String attribute() default "";

    Class<? extends Annotation> annotation() default Annotation.class;
}
