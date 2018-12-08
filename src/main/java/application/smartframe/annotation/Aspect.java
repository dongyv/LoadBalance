package application.smartframe.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * 该注解只能应用在类上
 * Created by xiachenhang on 2018/12/8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
