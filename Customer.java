
public class Customer {
	String name;
	String address;
	String customerID;
	String [] accounts;
	
	public Customer () {
		name = "Jeff";
		address = "1234 Potato Street";
		customerID = "";
		accounts = new String[10];
	}
	
	public Customer(String newName,String newAddress,String newID,String [] newAccounts) {
		name = newName;
		address = newAddress;
		customerID = newID;
		accounts = newAccounts;
	}
	
	public String toString() {
		String customerinfo = "";
		
		customerinfo = customerID + " , "
				+ name + " , "
				+ address + " , ";
		
		for(int i = 0;i<accounts.length;i++) {
			customerinfo += (accounts[i] + " ");
		}
		
		return customerinfo;
	}
	
	
	public String getName() {
		return name;
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
	
	public void setName(String x) {
		name = x;
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
