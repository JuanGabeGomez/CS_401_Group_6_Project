package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class AccountTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void ConstructorTest() {
		System.out.println("Constructor Test: ");
		System.out.println("Default Constructor: ");
		Account x = new Account();
		System.out.println(x.toString());
		System.out.println("Overloaded Constructor: ");
		Account y = new Account("Apple", "Gapple", "Bapple", "0.01");
		System.out.println(y.toString());
	}

}
