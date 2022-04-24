import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ATM {

	/* login: "bank#",ATM,"login","","card#,pin#"
	 * -> returns: "bank#","login","success",Account.toString()
	 * logout: "bank#","logout","",Account.toString()
	 * -> returns: "bank#","logout","success","Have a good day"
	*/
		
	//Variables
	Socket socket;
	Account user;
	String bankNumber = "-1";
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    long startTime;
    boolean loggedin = false;
    boolean debug = false;
    ATMCondition status;
	
    ATM()
    {
    	try {
			socket = new Socket("localhost", 1234);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bankNumber = "0";
		try {
			setUpIO();
		} catch (IOException e) {
			e.printStackTrace();
		}
		startTime = System.nanoTime();
		status = ATMCondition.WORKING;
    }
    
	ATM(Socket socket, String bankNumber)
	{
		this.bankNumber = bankNumber;
		this.socket = socket;
		try {
			setUpIO();
		} catch (IOException e) {
			e.printStackTrace();
		}
		startTime = System.nanoTime();
		status = ATMCondition.WORKING;
	}
	
	ATM(String host, int address, String bankNumber)
	{
		try {
			socket = new Socket(host, address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.bankNumber = bankNumber;
		try {
			setUpIO();
		} catch (IOException e) {
			e.printStackTrace();
		}
		startTime = System.nanoTime();
		status = ATMCondition.WORKING;
	}
	
	private void openShutter()
	{
		JOptionPane.showMessageDialog(null, "Shutter opening");
	}
	
	private void closeShutter()
	{
		JOptionPane.showMessageDialog(null, "Shutter closing");
	}
	
	private void calculateBills(int amount)
	{
		int numOfFifty = 0;
		int numOfTwenty = 0;
		int numOfTen = 0;
		while(amount >= 50)
		{
			amount -= 50;
			numOfFifty++;
		}
		while(amount >= 20)
		{
			amount -= 20;
			numOfTwenty++;
		}
		while(amount >= 10)
		{
			amount -= 10;
			numOfTen++;
		}
		if(numOfFifty > 0)
			JOptionPane.showMessageDialog(null, "50's given: " + numOfFifty + "\n"
				+ "20's given: " + numOfTwenty + "\n"
				+ "10's given: " + numOfTen);
		else if(numOfTwenty > 0)
			JOptionPane.showMessageDialog(null, "20's given: " + numOfTwenty + "\n"
					+ "10's given: " + numOfTen);
		else if(numOfTen > 0)
			JOptionPane.showMessageDialog(null, "10's given: " + numOfTen);
		else
			JOptionPane.showMessageDialog(null, "No money dispensed");
	}
	
	private void setUpIO() throws IOException
	{
		outputStream = socket.getOutputStream();
		objectOutputStream = new ObjectOutputStream(outputStream);
		inputStream = socket.getInputStream();
		objectInputStream = new ObjectInputStream(inputStream);
	}
	
	public void start()
	{
		login();
		while(true) {
			String[] ATMmenu = {"Check balance", "Deposit", "Withdrawal", "Logout"};
			int option = JOptionPane.showOptionDialog(null,
					"Select an option", 
					"ATM", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					ATMmenu,
					ATMmenu[ATMmenu.length - 1]);
			switch(option) {
			case 0:
				if(timeOut(startTime, System.nanoTime()))
				{
					JOptionPane.showMessageDialog(null, "Sorry, timed out due to inactivity");
					logout();
					return;
				}
				JOptionPane.showMessageDialog(null, "Available balance: " + user.getBalance());
				startTime = System.nanoTime();
				break;
			case 1:
				if(timeOut(startTime, System.nanoTime()))
					JOptionPane.showMessageDialog(null, "Sorry, timed out due to inactivity");
				else
					deposit();
				logout();
				clearUser();
				return;
			case 2:
				if(timeOut(startTime, System.nanoTime()))
					JOptionPane.showMessageDialog(null, "Sorry, timed out due to inactivity");
				else
					withdrawal();
				logout();
				clearUser();
				return;
			case 3:
				logout();
				clearUser();
				return;
			default:
				JOptionPane.showMessageDialog(null, "Sorry, not valid");
				break;
			}
		}
	}
	
	private void login()
	{
		int loginCt = 0;
		while(!loggedin && loginCt < 3)
		{
			String loginConfirm = new String("");
			String card = JOptionPane.showInputDialog("Enter card number insert card):");
			String pin = JOptionPane.showInputDialog("Enter pin:");
			String loginStr = new String(card + "," + pin);
    	
			//Send login request
			Message loginMsg = new Message(bankNumber, "ATM", "login", "", loginStr);
			try {
				objectOutputStream.writeObject(loginMsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	
			//Get login confirm
			Message loginReturn = null;
			try {
				loginReturn = (Message) objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		loginConfirm = loginReturn.getStatus();
    	
    		if(loginConfirm.equals("success"))
    		{
    			user = stringToAccount(loginReturn.getText());
    			loggedin = true;
    			return;
    		}
    		loginCt++;
		}
		if(!loggedin)
		{
			JOptionPane.showMessageDialog(null, "Login Failure, exceeded max 3 attempts");
		}
	}
	
	public void login(String card, String pin)
	{
		if(!debug)
			return;
		int loginCt = 0;
		while(!loggedin && loginCt < 3)
		{
			String loginConfirm = new String("");
			String loginStr = new String(card + "," + pin);
    	
			//Send login request
			Message loginMsg = new Message(bankNumber, "ATM", "login", "", loginStr);
			try {
				objectOutputStream.writeObject(loginMsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	
			//Get login confirm
			Message loginReturn = null;
			try {
				loginReturn = (Message) objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		loginConfirm = loginReturn.getStatus();
    	
    		if(loginConfirm.equals("success"))
    		{
    			user = stringToAccount(loginReturn.getText());
    			loggedin = true;
    			return;
    		}
    		loginCt++;
		}
	}
	
	private void deposit()
	{
		openShutter();
		int deposit = Integer.parseInt(JOptionPane.showInputDialog("Enter amount to deposit:"));
		closeShutter();
		int amount = user.getBalance();
		user.setBalance(amount + deposit);
	}
	
	public void deposit(int deposit)
	{
		if(!debug)
			return;
		int amount = user.getBalance();
		user.setBalance(amount + deposit);
	}
	
	private void withdrawal()
	{
		int amount = user.getBalance();
		int withdraw = Integer.parseInt(JOptionPane.showInputDialog("Enter amount to withdraw(available:" + amount + "):"));
		if(withdraw > amount && user.getType().equals("checking"))
			JOptionPane.showMessageDialog(null, "Sorry, that is too much, overdraft not enabled");
		else if (withdraw > 10000) {
			while(withdraw > 10000)
			{
				JOptionPane.showMessageDialog(null, "Sorry, withdrawals over $10,000 require employee approval");
				withdraw = Integer.parseInt(JOptionPane.showInputDialog("Enter new amount to withdraw(available:" + amount + ", 0 to cancel):"));
			}
			user.setBalance(amount - withdraw);
		}
		else if(withdraw % 10 != 0) {
			while(withdraw % 10 != 0)
			{
				JOptionPane.showMessageDialog(null, "Sorry, withdrawals must be a multiple of 10");
				withdraw = Integer.parseInt(JOptionPane.showInputDialog("Enter new amount to withdraw(available:" + amount + ", 0 to cancel):"));
			}
			user.setBalance(amount - withdraw);
		}
		else
			user.setBalance(amount - withdraw);
		calculateBills(withdraw);
	}
	
	public void withdrawal(int withdraw)
	{
		if(!debug)
			return;
		int amount = user.getBalance();
		if(withdraw > amount && user.getType().equals("checking"))
			JOptionPane.showMessageDialog(null, "Sorry, that is too much, overdraft not enabled");
		else if (withdraw > 10000) {
			while(withdraw > 10000)
			{
				JOptionPane.showMessageDialog(null, "Sorry, withdrawals over $10,000 require employee approval");
				withdraw = Integer.parseInt(JOptionPane.showInputDialog("Enter new amount to withdraw(available:" + amount + ", 0 to cancel):"));
			}
			user.setBalance(amount - withdraw);
		}
		else
			user.setBalance(amount - withdraw);
	}
	
	private void clearUser()
	{
		user = null;
	}
	
	private void logout()
	{
		int receipt = JOptionPane.showConfirmDialog(null, "Would you like a receipt?", null, JOptionPane.YES_NO_OPTION);
		if(receipt == JOptionPane.YES_OPTION)
			printReceipt();  

		//Send logout request
		Message logoutMsg = new Message(bankNumber, "ATM", "logout", "", user.toString());
		try {
			objectOutputStream.writeObject(logoutMsg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String logoutConfirm = new String();
		//Get login confirm
		Message logoutReturn = null;
		try {
			logoutReturn = (Message) objectInputStream.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		logoutConfirm = logoutReturn.getStatus();
		
		if(logoutConfirm.equals("success"))
			JOptionPane.showMessageDialog(null, "Have a great day!"); 
		else
			JOptionPane.showMessageDialog(null, "Logout error"); 
		
        //Close socket
        try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private Account stringToAccount(String customerParse)
	{
		String customerID = new String("");
		String type = new String("");
		String balance = new String("");
		int indexOfCustomer = 0;
		while(customerParse.charAt(indexOfCustomer) != ',' && indexOfCustomer < customerParse.length())
		{
			if(customerParse.charAt(indexOfCustomer) == ' ') {
    			indexOfCustomer++;
    			continue;	
    		}
    		customerID += customerParse.charAt(indexOfCustomer);
    		indexOfCustomer++;
		}
		indexOfCustomer++;
    	while(customerParse.charAt(indexOfCustomer) != ',' && indexOfCustomer < customerParse.length())
    	{
    		if(customerParse.charAt(indexOfCustomer) == ' ') {
    			indexOfCustomer++;
    			continue;	
    		}
    		type += customerParse.charAt(indexOfCustomer);
    		indexOfCustomer++;
    	}
    	indexOfCustomer++;
    	while(customerParse.charAt(indexOfCustomer) != '@' && indexOfCustomer < customerParse.length())
    	{
    		if(customerParse.charAt(indexOfCustomer) == ' ') {
    			indexOfCustomer++;
    			continue;	
    		}
    		balance += customerParse.charAt(indexOfCustomer);
    		indexOfCustomer++;
    	}
    	int trueBalance = Integer.parseInt(balance);
    	Account ret = new Account(customerID, type, trueBalance);
		return ret;
	}

	public Account debugStringToAccount(String customerParse)
	{
		if(!debug)
			return null;
		String customerID = new String("");
		String type = new String("");
		String balance = new String("");
		int indexOfCustomer = 0;
		while(customerParse.charAt(indexOfCustomer) != ',' && indexOfCustomer < customerParse.length())
		{
			if(customerParse.charAt(indexOfCustomer) == ' ') {
    			indexOfCustomer++;
    			continue;	
    		}
    		customerID += customerParse.charAt(indexOfCustomer);
    		indexOfCustomer++;
		}
		indexOfCustomer++;
    	while(customerParse.charAt(indexOfCustomer) != ',' && indexOfCustomer < customerParse.length())
    	{
    		if(customerParse.charAt(indexOfCustomer) == ' ') {
    			indexOfCustomer++;
    			continue;	
    		}
    		type += customerParse.charAt(indexOfCustomer);
    		indexOfCustomer++;
    	}
    	indexOfCustomer++;
    	while(customerParse.charAt(indexOfCustomer) != '@' && indexOfCustomer < customerParse.length())
    	{
    		if(customerParse.charAt(indexOfCustomer) == ' ') {
    			indexOfCustomer++;
    			continue;	
    		}
    		balance += customerParse.charAt(indexOfCustomer);
    		indexOfCustomer++;
    	}
    	int trueBalance = Integer.parseInt(balance);
    	Account ret = new Account(customerID, type, trueBalance);
		return ret;
	}
	
	private void printReceipt()
	{
		JOptionPane.showMessageDialog(null, "Account: " + user.getNumber() + "\n"
				+ "Account type: " + user.getType() + "\n"
				+ "Balance: $" + user.getBalance());
	}
	
	public String getReceipt()
	{
		if(!debug)
			return null;
		return "Account: " + user.getNumber() + "\n"
				+ "Account type: " + user.getType() + "\n"
				+ "Balance: $" + user.getBalance();
	}
	
	private boolean timeOut(long start, long end)
	{
		long actualTime = (end - start) / (1000000000 / 60);
		if(5 > actualTime)
			return true;
		return false;
	}
		
	public void enableDebug()
	{
		debug = true;
	}
	
	public boolean getDebug()
	{
		return debug;
	}
	
	public Socket getSocket()
	{
		if(!debug)
			return null;
		return socket;
	}
	
	public Account getUser()
	{
		if(!debug)
			return null;
		return user;
	}
	
	public String getBankNumber()
	{
		if(!debug)
			return null;
		return bankNumber;
	}
	
	public boolean getLoggedIn()
	{
		if(!debug)
			return false;
		return loggedin;
	}
	
	public void setStatus(ATMCondition stat)
	{
		status = stat;
	}
	
	public ATMCondition getStatus()
	{
		return status;
	}
}