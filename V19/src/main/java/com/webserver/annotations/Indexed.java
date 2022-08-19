package com.webserver.annotations;

import java.lang.annotation.*;

/**
 * @author sytsnb@gmail.com
 * @Date 2022 2022/8/19 15:07
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Indexed {
}
