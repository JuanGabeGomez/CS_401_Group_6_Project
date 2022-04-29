package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class CustomerTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void ConstructorTest() {
		System.out.println("Customer Constructor Test");
		System.out.println("Default Constructor: ");
		Customer x = new Customer();
		System.out.println(x.toString());
		//svg, chkg, card, pin, lname, fname, address
		System.out.println("Overloaded Constructor: ");
		Customer y = new Customer("12", "13", "1234", "0000", "Smith", "Bob", "123 Apple Street");
		System.out.println(y.toString());
		
		
	}

}
