package love.sola.fyoung.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ***********************************************
 * Created by Sola on 2016/3/20.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Command {

	String value() default "";

}
