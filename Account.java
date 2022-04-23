import java.util.Scanner;

public class Account {
	private String number;
	private int balance;
	private String type;
	
	public Account () {
		number = "0000";
		balance = 0;
		type = "0000";
	}
	
	public Account (String newID, String newType, int newMoney) {
		number = newID;
		balance = newMoney;
		type = newType;
	}
	
	public void addBalance (int newMoney) {
		balance += newMoney;
	}
	public void removeBalance (int x) {
		balance -= x;
	}
	
	public String toString() {
		String accountInfo = "";
		
		accountInfo = number + " , "
				+ type + " , "
				+ balance + " @ ";
		
		return accountInfo;
	}
	
	public void readAccountInfo(String AccountInfo) {
		Scanner sc = new Scanner(AccountInfo);
		
		String buffer;
		//id
		buffer = sc.next();
		number = buffer;
		buffer = sc.next();
		//type
		buffer = sc.next();
		type = buffer;
		buffer = sc.next();
		//balance
		buffer = sc.next();
		balance = Integer.parseInt(buffer);
		buffer = sc.next();
		
	}
	
	
	public String getNumber() {
		return number;
	}
	public int getBalance() {
		return balance;
	}
	public String getType() {
		return type;
	}
	
	public void setNumber(String x) {
		number = x;
	}
	public void setBalance(int x) {
		balance = x;
	}
	public void setType(String x) {
		type = x;
	}

}
