import java.util.*;

public class Customer {
	String lastName;
	String firstName;
	String address;
	String customerID;
	String card;
	String pin;
	String [] accounts;
	int accountsSize;
	
	public Customer () {
		firstName = "Jeff";
		lastName = "Jefferson";
		pin = "0000";
		card = "0000";
		address = "1234 Potato Street";
		customerID = "0000";
		accounts = new String[10];
		accountsSize = 0;
	}
	
	public Customer(String newFirstName, String newLastName,String newAddress,String newID,
			String newPin, String [] newAccounts) {
		firstName = newFirstName;
		lastName = newLastName;
		address = newAddress;
		customerID = newID;
		pin = newPin;
		accounts = newAccounts;
		accountsSize = newAccounts.length;
	}
	
	public String toString() {
		String customerinfo = "";
		
		customerinfo = customerID + " , "
				+ firstName + " , "
				+ lastName + " , "
				+ address + " , "
				+ card + " , "
				+ pin + " , ";
		
		for(int i = 0;i<accountsSize;i++) {
			customerinfo += (accounts[i] + " ");
		}
		customerinfo += " @ ";
		
		return customerinfo;
	}
	
	public void readString() {
		
	}
	
	public void readCustomerInfo(String customerInfo) {
		Scanner sc = new Scanner(customerInfo);
		accountsSize = 0;

		String buffer;
		//id
		buffer = sc.next();
		customerID = buffer;
		buffer = sc.next();
		//first name
		buffer = sc.next();
		firstName = buffer;
		buffer = sc.next();
		//last name
		buffer = sc.next();
		lastName = buffer;
		buffer = sc.next();
		buffer = sc.next();
		// address
		address = "";
		while (!buffer.contentEquals(",")) {
			address += (buffer + " ");
			buffer = sc.next();
		}
		// card
		buffer = sc.next();
		card = buffer;
		buffer = sc.next();
		// pin
		buffer = sc.next();
		pin = buffer;
		buffer = sc.next();
		buffer = sc.next();
		//accounts
		while (!buffer.contentEquals("@")) {
			addAccount(buffer);
			buffer = sc.next();
		}
	}
	
	public void addAccount(String newAccount) {
		accounts[accountsSize] = newAccount;
		accountsSize++;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getCustomerID() {
		return customerID;
	}
	public String[] getAccounts() {
		return accounts;
	}
	
	public void setFirstName(String x) {
		firstName = x;
	}
	public void setLastName(String x) {
		firstName = x;
	}
	public void setAddress(String x) {
		address = x;
	}
	public void setCustomerID(String x) {
		customerID = x;
	}
	public void setAccounts(String[] x) {
		accounts = x;
	}
	
}
