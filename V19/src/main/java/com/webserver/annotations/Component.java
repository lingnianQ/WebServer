package com.webserver.annotations;

import java.lang.annotation.*;

/**
 * @author sytsnb@gmail.com
 * @Date 2022 2022/8/19 15:07
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";

}
