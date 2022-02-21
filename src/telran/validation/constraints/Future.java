package telran.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * 
 * validation constraint
 * field may be only LocalDate greater than current date
 *
 */
public @interface Future {
	String message() default "Only Local Data greater than current date";
}
