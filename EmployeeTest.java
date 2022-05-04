import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class EmployeeTest {

	
	@Test
	public void testconstructor() {
		Employee employee1 = new Employee();
		System.out.println("Default Constructor ");
		System.out.println(employee1.toString());
		
		
		Employee employee2 = new Employee("Jack","PSWD","teller","0");
		System.out.println("Overload Constructor");
		System.out.println(employee2.toString());
		
		
	}

}
