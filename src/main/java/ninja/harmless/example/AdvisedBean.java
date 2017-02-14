package ninja.harmless.example;

import ninja.harmless.example.aspect.AdvisedMethod;
import ninja.harmless.example.aspect.LogMe;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author bnjm@harmless.ninja - 2/14/17.
 */
@Component
public class AdvisedBean {

    @AdvisedMethod
    public void sayHello() {
        System.out.println("\n\t" + this.getClass().getName()+ ".sayHello() invocated!");
    }

    @AdvisedMethod
    public void throwException() throws IOException {
        throw new IOException();
    }

    @AdvisedMethod
    public int aroundExample(int input) {
        System.out.println("input: " + input);
        return input;
    }

    @LogMe
    public void logTheException(String str) {
        throw new IllegalStateException("Damn! Nasty exceptions...");
    }
}
