package ninja.harmless.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Actual simple logging aspect implementation.
 *
 * Pointcuts:
 *
 * Pointcuts define where a an aspect advice should be applied.
 * Spring AOP uses ApectJs syntax to define pointscuts but be advised that Springs AOP.
 * Springs AOP implementation only supports method join points. This is due to the fact that Spring AOP is based on dynamic proxies.
 * If any more than method interception is needed (for example changes on object fields) AspectJ itself must be used.
 *
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html#aop-pointcuts
 *
 * Spring extends the AspectJ syntax with the bean() designator which allows you to identify beans by their ID in a
 * examplePointcut expression.
 *
 * The examplePointcut expression can be either defined directly in one of the AspectJ advice annotations or in the special
 * @Pointcut annotation. @Pointcut can be used for frequently used examplePointcut within an aspect.
 *
 * Available AspectJ annotations:
 * @After               Advice method is called after returning of after an exception is thrown
 * @AfterReturning      Advice method is called after returning
 * @AfterThrowing       Advice method is called after an exception was thrown
 * @Around              Wraps the advised method so you are able to intervene before and after method invocation
 * @Before              Advice method is called before the advised method is called
 *
 *
 * @author bnjm@harmless.ninja - 2/14/17.
 */
@Aspect
@Component
public class SimpleAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Showcasing @Pointcut on an annotation |  on any method, no matter what arguments (..) which uses the @AdvisedMethod annotation
    @Pointcut("@annotation(ninja.harmless.example.aspect.AdvisedMethod) && execution(* * (..))")
    public void examplePointcut() {}


    @Before("examplePointcut()")
    public void beforeInvocation() {
        System.out.println("\n--> BEFORE ADVICE METHOD CALLED!");
    }

    // Showcasing pointcut designation directly in the advice annotation (advises the same as examplePointcut()!
    @After("@annotation(ninja.harmless.example.aspect.AdvisedMethod) && execution(* * (..))")
    public void afterInvocation() {
        System.out.println("\n--> AFTER ADVICE METHOD CALLED!\n");
    }

    @AfterThrowing("examplePointcut()")
    public void afterThrowing() {
        System.out.println("--> OH SNAP AN EXCEPTION OCCURED!\n");
    }


    @Around("execution(* ninja.harmless.example.AdvisedBean.aroundExample(**))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object returnObject = null;
        try {
            System.out.println("--> Around: before invocation");
            returnObject = pjp.proceed(); // let the method proceed
        } catch (Throwable t) {
            throw t; // catch exception if there is one and rethrow
        } finally {
            // After invocation
            System.out.println("--> Around: after invocation\n Output will be " + returnObject + " + 1");
        }
        // Manipulate output
        return (int) returnObject + 1;

    }


    // Logger Example:
    @AfterThrowing(pointcut = "@annotation(ninja.harmless.example.aspect.LogMe) && execution(* * (..))", throwing = "e")
    public void logExceptionAfterThrowing(JoinPoint jp, Throwable e) {
        logger.error("{} in {}.{}({}) thrown. Cause: {}. ",
                e.getMessage(), jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                Arrays.toString(jp.getArgs()),
                e.getCause(),e);
    }


}
