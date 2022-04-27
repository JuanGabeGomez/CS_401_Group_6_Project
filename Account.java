public class Account {
	
	private String acctNum;
	private String type;
	private String status;
	private String balance;
	
	// Constructor
	public Account () {
		this.acctNum = "0000";
		this.type = "Undefined";
		this.status = "Not used";
		this.balance = "0.00";
	}
	
	// Overloaded constructor
	public Account (String newAcctNum, String newType, String newStatus, String newMoney) {
		setAcctNum(newAcctNum);
		setType(newType);
		setStatus(newStatus);
		setBalance(newMoney);
	}
	
	// Return a string with all variables separated by commas
	public String toString() {
		String accountInfo = "";
		
		accountInfo = getAcctNum() + ","
				+ getType() + ","
				+ getStatus() + ","
				+ getBalance();
		
		return accountInfo;
	}
	
	// Public getter methods
	
	public String getAcctNum() {
		return acctNum;
	}
	public String getType() {
		return type;
	}
	public String getStatus() {
		return status;
	}
	public String getBalance() {
		return balance;
	}
	
	// Public setter methods 

	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
	public void setBalance(String newBal) {
		this.balance = newBal;
	}

	// Private setter methods
	
	private void setAcctNum(String newAcctNum) {
		this.acctNum = newAcctNum;
	}
	private void setType(String newType) {
		this.type = newType;
	}
}