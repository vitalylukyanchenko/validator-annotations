package telran.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * validation constraint
 * defining min value of a number field (int, long, float, double)
 */
public @interface Min {
	int value();
	String message() default "min constraint violation";
}
