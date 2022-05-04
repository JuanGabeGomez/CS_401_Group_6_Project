import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class EmployeesDataTest {
	


	@Test
	public void  testconstructor () {
		EmployeesData Empdata = new EmployeesData();
		System.out.println();
		System.out.println("Constructor Test: ");
		Empdata.addOrModifyEmployee("Jack","pswd","teller","0");
		Empdata.addOrModifyEmployee("Paul","abcd","officer","1");
		Empdata.addOrModifyEmployee("John","xyz", "manager","2");
		
		System.out.println(Empdata.toString());
	}
	@Test
	public void RemoveEmpTest () {
		EmployeesData Empdata = new EmployeesData();
		System.out.println("Remove Employee Test: ");
		Empdata.addOrModifyEmployee("Jack","pswd","teller","0");
		Empdata.addOrModifyEmployee("Paul","abcd","officer","1");
		Empdata.addOrModifyEmployee("John","xyz", "manager","2");
		
		Empdata.removeEmployee("Jack");
		
		System.out.println(Empdata.toString());
		
	}
	@Test
	public void EmployeePositionTest () {
		
		EmployeesData Empdata = new EmployeesData();
		System.out.println("Employee by Position Test: ");
		
		Empdata.addOrModifyEmployee("Paul","abcd","officer","1");
		Empdata.addOrModifyEmployee("John","xyz", "manager","2");
		
		System.out.println(Empdata.getEmployeesByPosition("2"));	
		
	}
	@Test
	public void LoadDataTest() {
		System.out.println("Load Data Test: ");
		EmployeesData  Empdata = new EmployeesData();
		Empdata.loadData("Bank1-Employees.txt");
		System.out.println(Empdata.toString());
		
	}
	
}

