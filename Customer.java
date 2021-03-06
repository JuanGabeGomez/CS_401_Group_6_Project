public class Customer {
	
	protected String svgAcctNum;
	protected String chkgAcctNum;
	protected String cardNum;
	protected String pinNum;
	protected String lName;
	protected String fName;
	protected String address;
	
	// Constructor
	public Customer () {
		svgAcctNum = "Undefined";
		chkgAcctNum = "Undefined";
		cardNum = "Undefined";
		pinNum = "Undefined";
		lName = "Undefined";
		fName = "Undefined";
		address = "Undefined";
	}
	
	// Overloaded constructor
	public Customer(String newSvgAcctNum,String newChkgAcctNum,String newCardNum,String newPinNum,String newLName,String newFName,String newAddress) {
		setSvgAcctNum(newSvgAcctNum);
		setChkgAcctNum(newChkgAcctNum);
		setCardNum(newCardNum);
		setPinNum(newPinNum);
		setLName(newLName);
		setFName(newFName);
		setAddress(newAddress);
	}
	
	// Return a string with all variables separated by commas
	public String toString() {
		String customerinfo = "";
		
		customerinfo = getSvgAcctNum() + ","
					+ getChkgAcctNum() + ","
					+ getCardNum() + ","
					+ getPinNum() + ","
					+ getLName() + ","
					+ getFName() + ","
					+ getAddress();
		
		return customerinfo;
	}
	
	// Public getter methods
	
	public String getSvgAcctNum() {
		return svgAcctNum;
	}
	public String getChkgAcctNum() {
		return chkgAcctNum;
	}
	public String getCardNum() {
		return cardNum;
	}
	public String getPinNum() {
		return pinNum;
	}
	public String getLName() {
		return lName;
	}
	public String getFName() {
		return fName;
	}
	public String getAddress() {
		return address;
	}
	
	// Public setter methods
	
	public void setPinNum(String newPinNum) {
		pinNum = newPinNum;
	}
	public void setLName(String newLName) {
		lName = newLName;
	}
	public void setFName(String newFName) {
		fName = newFName;
	}
	public void setAddress(String newAddress) {
		address = newAddress;
	}
	
	// Private setter methods
	
	private void setSvgAcctNum(String newSvgAcctNum) {
		svgAcctNum = newSvgAcctNum;;
	}
	private void setChkgAcctNum(String newChkgAcctNum) {
		chkgAcctNum = newChkgAcctNum;
	}
	private void setCardNum(String newCardNum) {
		cardNum = newCardNum;
	}
}