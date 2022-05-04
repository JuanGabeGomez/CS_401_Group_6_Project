/*
 * Majority of the codes are from Threaded Sockets and Object Over Socket code
 * provided in class by the professor.
 */
import java.io.*;
import java.math.RoundingMode;
import java.net.*;
import java.text.DecimalFormat;
import java.util.*;

// Client class
class Desktop {
	
	// driver code
	public static void main(String[] args)	{
		Scanner sc = new Scanner(System.in);
		String line = null;
		
		//while(!"exit".equalsIgnoreCase(line)) {
			System.out.println("Enter<desktop,atm, or exit>: ");
			line = sc.nextLine();
			
			if("desktop".equalsIgnoreCase(line)) {
				desktopTester();
			}
			if("atm".equalsIgnoreCase(line)) {
				atmTester();
			}
		//}
		
		// establish a connection by providing host and port
		// number
		/*try (Socket svrSocket = new Socket("localhost", 1234)) {
			
			// Output stream socket.
	        OutputStream outputStream = svrSocket.getOutputStream();

	        // Create object output stream from the output stream to send an object through it
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

	        // get the input stream from the connected socket
	        InputStream inputStream = svrSocket.getInputStream();

	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
	        
			// object of scanner class
			Scanner sc = new Scanner(System.in);
			String line = null;
			Message message = null;
			Message atmMsg = null;
			*/	
	}
	
	// Desktop tester
	private static void desktopTester() {
		Scanner sc = new Scanner(System.in);
		String line = null;
		Message message = null;
		double newBal = 0.00;
		
		try (Socket svrSocket = new Socket("localhost", 1234)) {
			
			// Output stream socket.
	        OutputStream outputStream = svrSocket.getOutputStream();

	        // Create object output stream from the output stream to send an object through it
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

	        // get the input stream from the connected socket
	        InputStream inputStream = svrSocket.getInputStream();

	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
	        
			// object of scanner class
			
	        do{
	        	//Scanner sc = new Scanner(System.in);
	        	//String line = null;
		
	        	// reading from user
	        	System.out.print("Username(Cat): ");
	        	line = sc.nextLine();
	        	message = new Message("1","desktop","login","",line);
		
	        	System.out.print("Password(<1234>): ");
	        	line = sc.nextLine();
	        	message.setText(message.getText() + "," + line);
		
	        	System.out.println(message.getText());
	        	objectOutputStream.writeObject(message);
	        	message = (Message)objectInputStream.readObject();
	        	System.out.println("Login: " + message.getStatus() + "\n");
	        	if(!"success".equalsIgnoreCase(message.getStatus())) {
	        		
	        		System.out.println("Incorrect Username/Password");
	        	}
		
	        	/*if("login".equalsIgnoreCase(line)) {
					message = new Message(line,"","");
					objectOutputStream.writeObject(message);
					message = (Message)objectInputStream.readObject();
					System.out.println("Login: " + message.getStatus() + "\n");
				}*/
	        }
	
	        while (!"success".equalsIgnoreCase(message.getStatus()));
	        //while (!"login".equalsIgnoreCase(line));
	
	        while(!"logout".equalsIgnoreCase(line)){
	        	String accountNum = "";
        		String cardNum = "";
        		String pin = "";
        		
	        	
        		System.out.println("\nCommand Option:"
        						+ "\n1. inquiry" 
        						+ "\n2. customer+"
        						+ "\n3. update"
        						+ "\n4. logout");
	        	System.out.print("Message(<logout> to quit): ");
	        	line = sc.nextLine();
		
	        	if("logout".equalsIgnoreCase(line)) {
	        		message = new Message("1","desktop",line,"","");
	        		objectOutputStream.writeObject(message);
	        		message = (Message)objectInputStream.readObject();
	        		System.out.println("Logout: " + message.getStatus());
	        	}
	        	if("a-duck".equalsIgnoreCase(line)) {
	        		message = new Message("1","desktop","authorization","","Duck,3456");
	        		objectOutputStream.writeObject(message);
	        		message = (Message)objectInputStream.readObject();
	        		System.out.println("Authorization: " + message.getStatus());
	        	}
	        	if("a-dog".equalsIgnoreCase(line)) {
	        		message = new Message("1","desktop","authorization","","Dog,2345");
	        		objectOutputStream.writeObject(message);
	        		message = (Message)objectInputStream.readObject();
	        		System.out.println("Authorization: " + message.getStatus());
	        	}
	        	if("inquiry".equalsIgnoreCase(line)) {
	        		System.out.print("Please enter the account#<4567>: ");
	        		accountNum = sc.nextLine();
	        		System.out.print("Card#<888888>: ");
	        		cardNum = sc.nextLine();
	        		System.out.print("Enter pin<0202>: ");
	        		pin = sc.nextLine();
	        		String text = accountNum + "," + cardNum + "," + pin;
	        		//message = new Message("1","desktop","inquiry","","4567,888888,0202");
	        		message = new Message("1","desktop","inquiry","",text);
	        		objectOutputStream.writeObject(message);
	        		message = (Message)objectInputStream.readObject();
	        		//System.out.println("Customer&Acct Info: " + message.getText());
	        		//message = (Message)objectInputStream.readObject();
	        		//System.out.println("AccountInfo: " + message.getText());
	        		int numInd = 11;
	        		int counter = 0;
	        		String[] splitText = message.getText().split(",");
	        		String[] acctInfo = null;
			
	        		acctInfo = new String [numInd];
				
	        		for(String a : splitText) {
	        			acctInfo[counter] = a;
	        			counter++;
	        		}
	        		System.out.println("\nSaving Account#: "+ acctInfo[0]
	        						+ "\nChecking Account#: " + acctInfo[1]
    								//+ "\nCard Number: " + acctInfo[2]
    								//+ "\nPin Number: " + acctInfo[3]
    								+ "\nLName: " + acctInfo[4] + ", " + acctInfo[5]
    								+ "\nAddress: " + acctInfo[6]
    								+ "\nAccount Inquired: " + acctInfo[7]
    								+ "\nAccount Balance: " + acctInfo[10]);
	        	}
		
	        	// Only use if an inquiry has been initilize
	        	if("customer+".equalsIgnoreCase(line)) {
	        		int numInd = 11;
			
	        		int svgAcctInd = 0;
	        		int chkgAcctInd = 1;
	        		int cardInd = 2;
	        		int pinInd = 3;
	        		int lNameInd = 4;
	        		int fNameInd = 5;
	        		int addressInd = 6;
	        		int acctNumInd = 7;
	        		int acctTypeInd = 8;
	        		int acctStatInd = 9;
	        		int acctBalInd = 10;
			
	        		int counter = 0;
	        		String[] splitText = message.getText().split(",");
	        		String[] acctInfo = null;
			
	        		acctInfo = new String [numInd];
				
	        		for(String a : splitText) {
	        			acctInfo[counter] = a;
	        			counter++;
	        		}
	        		System.out.print("\ndeposit or withdraw:");
	        		double amt = sc.nextDouble();
	        		
	        		newBal = Double.parseDouble(acctInfo[acctBalInd]) + amt;
		        		
		        	System.out.println("\nSaving Account#: "+ acctInfo[0]
	    						+ "\nChecking Account#: " + acctInfo[1]
								//+ "\nCard Number: " + acctInfo[2]
								//+ "\nPin Number: " + acctInfo[3]
								+ "\nLName: " + acctInfo[4] + ", " + acctInfo[5]
								+ "\nAddress: " + acctInfo[6]
								+ "\nAccount Inquired: " + acctInfo[7]
								+ "\nAccount Balance: " + twoDecimal(String.valueOf(newBal)));
	        	}
	        	
	        	if("update".equalsIgnoreCase(line)) {
	        		int numInd = 11;
			
	        		int counter = 0;
	        		String[] splitText = message.getText().split(",");
	        		String[] acctInfo = null;
			
	        		acctInfo = new String [numInd];
				
	        		for(String a : splitText) {
	        			acctInfo[counter] = a;
	        			counter++;
	        		}
	        		
	        		String convert = twoDecimal(String.valueOf(newBal));
	        		Customer upCust = new Customer(acctInfo[0],acctInfo[1],acctInfo[2],acctInfo[3]
	        										,acctInfo[4],acctInfo[5],acctInfo[6]);
	        		Account upAccount = new Account(acctInfo[7],acctInfo[8],acctInfo[9],convert);
	        		message = new Message("1","desktop","update","",upCust.toString()+","+upAccount.toString());
	        		objectOutputStream.writeObject(message);
	        		message = (Message)objectInputStream.readObject();
	        		if("success".equalsIgnoreCase(message.getStatus())) {
	        			System.out.println("Update: " + message.getStatus());
	        		}
	        		else {
	        			System.out.println("Update failed!");
	        		}
	        	}
	        }
	        message = null;
			objectOutputStream.writeObject(message);
			
			if (objectOutputStream != null) {
		    	objectOutputStream.close();
		    	System.out.println("Desktop output stream closed");
			}
			if (objectInputStream != null) {
				objectInputStream.close();
				System.out.println("Desktop input stream closed");
				svrSocket.close();
				System.out.println("Desktop server socket closed");
			}
			
			// closing the scanner object
			sc.close();
		}
		
		catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	// ATM tester
	private static void atmTester() {
		//String atmStatus = "";
		
		// object of scanner class
		Scanner sc = new Scanner(System.in);
		String line = null;
		Message atmMsg = null;
		
		try (Socket svrSocket = new Socket("localhost", 1234)) {
			
			// Output stream socket.
	        OutputStream outputStream = svrSocket.getOutputStream();

	        // Create object output stream from the output stream to send an object through it
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

	        // get the input stream from the connected socket
	        InputStream inputStream = svrSocket.getInputStream();

	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
	        while(!"atm-logout".equalsIgnoreCase(line)){
	        	System.out.print("ATM Console: ");
	        	line = sc.nextLine();
	        	
	        	if("atm-login".equalsIgnoreCase(line)) {
	        		atmMsg = new Message("1","atm","login","","777777,0201");
	        		objectOutputStream.writeObject(atmMsg);
	        		atmMsg = (Message)objectInputStream.readObject();
	        		System.out.println("AccountInfo: " + atmMsg.getText() + "\n");
	        	}

	        	if("atm+".equalsIgnoreCase(line)) {
	        		//account#,type,status,balance
	        		int counter = 0;
	        		String[] splitText = atmMsg.getText().split(",");
	        		String[] acctInfo = null;
			
	        		acctInfo = new String [4];
				
	        		for(String a : splitText) {
	        			acctInfo[counter] = a;
	        			counter++;
	        		}
	        		Double bal = Double.parseDouble(acctInfo[3]);
	        		bal = bal + 5.00;
	        		acctInfo[3] = bal.toString();
	        		System.out.println("Account #: " + acctInfo[0]
	        				+ "\nType: " + acctInfo[1]
	        						+ "\nStatus: " + acctInfo[2]
	        								+ "\nBalance: " + acctInfo[3]);
	        		atmMsg.setText(acctInfo[0]+ "," + acctInfo[1]+ "," + acctInfo[2] + "," + acctInfo[3]);
	        	}
		
	        	if("atm-logout".equalsIgnoreCase(line)) {
	        		//System.out.println(atmMsg.getText());
	        		String temp = atmMsg.getText();
	        		atmMsg = new Message("1","ATM","logout","",temp);
	        		objectOutputStream.writeObject(atmMsg);
	        		atmMsg = (Message)objectInputStream.readObject();
	        		System.out.println("AccountLogout: " + atmMsg.getText() + "\n");
	        		atmMsg = null;
	        	}
	        }
	        
	        atmMsg = null;
	    	objectOutputStream.writeObject(atmMsg);
	    	
	    	if (objectOutputStream != null) {
	        	objectOutputStream.close();
	        	System.out.println("ATM output stream closed");
	    	}
	    	if (objectInputStream != null) {
	    		objectInputStream.close();
	    		System.out.println("ATM input stream closed");
	    		svrSocket.close();
	    		System.out.println("ATM server socket closed");
	    	}
	    	
	    	// closing the scanner object
	    	sc.close();
		}
		
		catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private static String twoDecimal(String number) {
		double temp = Double.parseDouble(number);
		DecimalFormat formatDecimal = new DecimalFormat("0.00");
		formatDecimal.setRoundingMode(RoundingMode.HALF_UP);
		String adjusted = formatDecimal.format(temp);
		
		return adjusted;
	}
}

			
			
			

