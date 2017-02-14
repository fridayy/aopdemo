package ninja.harmless.example.aspect;

import java.lang.annotation.*;

/**
 * Indicates an aspect adviced method
 *
 * @author bnjm@harmless.ninja - 2/14/17.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdvisedMethod {
}
