package rs.pscode.pomodorofire.spring.conditionals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Conditional(value = FirebaseConditionImpl.class)
public @interface FirebaseCondition {

}
