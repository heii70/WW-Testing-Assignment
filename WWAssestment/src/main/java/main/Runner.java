package main;
import org.testng.ITestNGListener;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

//import tests.Question2;

public class Runner {

	public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { Question2.class });
        testng.addListener((ITestNGListener) tla);
        testng.run();
    }

}
