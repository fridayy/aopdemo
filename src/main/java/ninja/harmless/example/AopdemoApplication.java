package ninja.harmless.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}
}

@Component
class Clrunner implements CommandLineRunner {

	@Autowired
	AdvisedBean advisedBean;


	@Override
	public void run(String... args) throws Exception {
		// Before and After example
		System.out.println("CALLING ADVISED METHOD....");
		advisedBean.sayHello();


		// AfterThrowing example
		System.out.println("\n\nCALLING ADVISED METHOD THROWING AN EXCEPTION\n");
		try {
			advisedBean.throwException();
		} catch (Exception e) {
			// Do nothing to show the advice output more clearly
		}


		// Around example (manipulating the output of a method)
		System.out.println("\n\nAROUND EXAMPLE:\n");
		int output = advisedBean.aroundExample(3);
		System.out.println("output: " + output);


		//Logging example
		try {
			advisedBean.logTheException("abc");
		} catch (Exception e) {
			// catch to show the output more clearly
		}
	}
}
