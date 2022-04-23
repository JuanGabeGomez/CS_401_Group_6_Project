
public class Customer {
	protected int svgAcctNum;
	protected int chkgAcctNum;
	protected int cardNum;
	protected int pinNum;
	protected String lName;
	protected String fName;
	protected String address;
	
	public Customer () {
		svgAcctNum = 0000;
		chkgAcctNum = 0000;
		cardNum = 000000;
		pinNum = 0000;
		lName = "Undefined";
		fName = "Undefined";
		address = "Undefined";
	}
	
	public Customer(int newSvgAcctNum,int newChkgAcctNum,int newCardNum,int newPinNum,String newLName,String newFName,String newAddress) {
		setSvgAcctNum(newSvgAcctNum);
		setChkgAcctNum(newChkgAcctNum);
		setCardNum(newCardNum);
		setPinNum(newPinNum);
		setLName(newLName);
		setFName(newFName);
		setAddress(newAddress);
	}
	
	public String toString() {
		String customerinfo = "";
		
		customerinfo = getSvgAcctNum() + ","
					+ getChkgAcctNum() + ","
					+ getCardNum() + ","
					+ getPinNum() + ","
					+ getLName() + ","
					+ getFName() + ","
					+ getAddress() + ",";
		
		return customerinfo;
	}
	
	
	public int getSvgAcctNum() {
		return svgAcctNum;
	}
	public int getChkgAcctNum() {
		return chkgAcctNum;
	}
	public int getCardNum() {
		return cardNum;
	}
	public int getPinNum() {
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
	
	private void setSvgAcctNum(int newSvgAcctNum) {
		svgAcctNum = newSvgAcctNum;;
	}
	private void setChkgAcctNum(int newChkgAcctNum) {
		chkgAcctNum = newChkgAcctNum;
	}
	public void setCardNum(int newCardNum) {
		cardNum = newCardNum;
	}
	public void setPinNum(int newPinNum) {
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
}