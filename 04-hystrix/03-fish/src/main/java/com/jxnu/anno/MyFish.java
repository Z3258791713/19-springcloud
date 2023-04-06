package com.jxnu.anno;

import java.lang.annotation.*;

/**
 * 熔断器切面注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
@Inherited
public @interface MyFish {
}
