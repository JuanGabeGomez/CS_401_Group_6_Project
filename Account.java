
public class Account {
	String number;
	String pin;
	int balance;
	String holder;
	
	public Account () {
		number = "";
		pin = "";
		balance = 0;
		holder = "";
	}
	
	public Account (String newID,String newPIN,String newName,int newMoney) {
		number = newID;
		pin = newPIN;
		balance = newMoney;
		holder = newName;
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
				+ pin + " , "
				+ holder + " , "
				+ balance;
		
		return accountInfo;
	}
	
	public String getNumber() {
		return number;
	}
	public String getPIN() {
		return pin;
	}
	public int getBalance() {
		return balance;
	}
	public String getHolder() {
		return holder;
	}
	
	public void setNumber(String x) {
		number = x;
	}
	public void setPIN(String x) {
		pin = x;
	}
	public void setBalance(int x) {
		balance = x;
	}
	public void setHolder(String x) {
		holder = x;
	}

}
