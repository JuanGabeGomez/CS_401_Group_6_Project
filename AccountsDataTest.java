package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class AccountsDataTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void ConstructorTest() {
		System.out.println();
		System.out.println("Constructor Test: ");
		AccountsData data = new AccountsData();
		data.addOrModifyAccount("0000", "Checking", "in use", "0");
		data.addOrModifyAccount("0001", "Checking", "in use", "0");
		data.addOrModifyAccount("0002", "Checking", "in use", "0");
		data.addOrModifyAccount("0003", "Checking", "in use", "0");
		
		System.out.println(data.toString());
	}
	
	@Test
	public void RemoveTest() {
		System.out.println();
		System.out.println("Remove Test: ");
		AccountsData data = new AccountsData();
		data.addOrModifyAccount("0000", "Checking", "in use", "0");
		data.addOrModifyAccount("0001", "Checking", "in use", "0");
		data.addOrModifyAccount("0002", "Checking", "in use", "0");
		data.addOrModifyAccount("0003", "Checking", "in use", "0");
		
		data.removeAccount("0001");
		
		System.out.println(data.toString());
	}
	@Test
	public void getAccountTest() {
		System.out.println();
		System.out.println("Get Account Test: ");
		AccountsData data = new AccountsData();
		data.addOrModifyAccount("0000", "Checking", "in use", "0");
		data.addOrModifyAccount("0001", "Checking", "in use", "0");
		data.addOrModifyAccount("0002", "Checking", "in use", "0");
		data.addOrModifyAccount("0003", "Checking", "in use", "0");
		
		System.out.println(data.getAccount("0001"));
	}
	
	@Test
	public void IncreaseArrayTest() {
		System.out.println();
		System.out.println("Increase Array Test: ");
		AccountsData data = new AccountsData();
		data.addOrModifyAccount("0000", "Checking", "in use", "0");
		data.addOrModifyAccount("0001", "Checking", "in use", "0");
		data.addOrModifyAccount("0002", "Checking", "in use", "0");
		data.addOrModifyAccount("0003", "Checking", "in use", "0");
		data.addOrModifyAccount("0004", "Checking", "in use", "0");
		data.addOrModifyAccount("0005", "Checking", "in use", "0");
		data.addOrModifyAccount("0006", "Checking", "in use", "0");
		data.addOrModifyAccount("0007", "Checking", "in use", "0");
		data.addOrModifyAccount("0008", "Checking", "in use", "0");
		
		System.out.println(data.toString());
	}
	
	@Test
	public void LoadDataTest() {
		System.out.println("Data Test: ");
		AccountsData data = new AccountsData();
		data.loadData("Bank1-Accounts.txt");
		
		System.out.println(data.toString());
	}
}
