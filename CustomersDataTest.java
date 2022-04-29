package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class CustomersDataTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void ConstructorTest() {
		System.out.println("Customer Data Test: ");
		CustomersData data = new CustomersData();
		data.addOrModifyCustomer("0000", "0000", "0000", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0001", "0001", "0001", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0002", "0002", "0002", "0000", "Smith", "Bob", "123 Apple Street");
		
		System.out.println(data.toString());
		System.out.println("Modifying 0001");
		data.addOrModifyCustomer("0001", "0001", "0001", "0000", "Taters", "Tots", "123 Apple Avenue");
		System.out.println(data.toString());
		
	}
	
	@Test
	public void RemoveCustomerTest() {
		System.out.println("Remove Customer Test: ");
		CustomersData data = new CustomersData();
		data.addOrModifyCustomer("0000", "0000", "0000", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0001", "0001", "0001", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0002", "0002", "0002", "0000", "Smith", "Bob", "123 Apple Street");
		data.removeCustomer("0001");
		
		System.out.println(data.toString());
		
		
	}
	
	@Test
	public void IncreaseArrayTest() {
		System.out.println("Increase Array Test: ");
		CustomersData data = new CustomersData();
		data.addOrModifyCustomer("0000", "0000", "0000", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0001", "0001", "0001", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0002", "0002", "0002", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0003", "0003", "0003", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0004", "0004", "0004", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0005", "0005", "0005", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0006", "0006", "0006", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0007", "0007", "0007", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0008", "0008", "0008", "0000", "Smith", "Bob", "123 Apple Street");
		
		System.out.println(data.toString());
		
	}
	
	@Test
	public void LoadDataTest() {
		System.out.println("Customer Data Test: ");
		CustomersData data = new CustomersData();
		data.loadData("Bank1-Customers.txt");
		
		System.out.println(data.toString());
	}

	
	@Test
	public void SearchCustomerTest() {
		System.out.println("Search Customer Test: ");
		CustomersData data = new CustomersData();
		data.addOrModifyCustomer("0000", "0000", "0000", "0000", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0001", "0001", "0001", "0001", "Smith", "Bob", "123 Apple Street");
		data.addOrModifyCustomer("0002", "0002", "0002", "0002", "Smith", "Bob", "123 Apple Street");
		
		System.out.println();
		System.out.println(data.toString());
		System.out.println("Found " + data.getCustsByChkgAcct("0001"));
		System.out.println("Found " + data.getCustsBySvgAcct("0002"));
		
		if (data.isAccountBelongToCard("0000", "0000", "0000"))
			System.out.println("Account belongs to 0000");
		else
			System.out.println("Account DOES NOT belongs to 0000");
	}
}
