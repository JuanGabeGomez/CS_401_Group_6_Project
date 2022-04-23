public class Account {
	private String acctNum;
	private String type;
	private String status;
	private String balance;
	
	public Account () {
		this.acctNum = "0000";
		this.type = "Undefined";
		this.status = "Not used";
		this.balance = "0.00";
	}
	
	public Account (String newAcctNum, String newType, String newStatus, String newMoney) {
		this.acctNum = newAcctNum;
		this.balance = newMoney;
		this.status = newStatus;
		this.type = newType;
	}
	
	/*
	public void addBalance (int newMoney) {
		balance += newMoney;
	}
	public void removeBalance (int x) {
		balance -= x;
	}
	*/
	
	public String toString() {
		String accountInfo = "";
		
		accountInfo = acctNum + " , "
				+ type + " , "
				+ status + ","
				+ balance;
		
		return accountInfo;
	}
	
	/*
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
	*/
	
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

	public void setAcctNum(String newAcctNum) {
		this.acctNum = newAcctNum;
	}
	public void setType(String newType) {
		this.type = newType;
	}
	public void setStatus(String newStatus) {
		this.balance = newStatus;
	}
	public void setBalance(String newBal) {
		this.balance = newBal;
	}

}