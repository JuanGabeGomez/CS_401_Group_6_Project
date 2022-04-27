import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//import javax.swing.JOptionPane;

public class CustomersData {
	// Data fields
	
	/** The current number of customers in the array */
	private int numCusts;
		
	/** The array to contain the Customers */
	private Customer[] custArray;
		
	/** The name of the data file that contains customer data */
	private String sourceName;
		
	/** Boolean flag to indicate whether the CustomerData was
		modified since it was last saved. */
	private boolean modified;
		
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public CustomersData() {
		this.numCusts = 0;
		this.custArray = new Customer[7];
	}
		
	// Return a string with number of Customers in the array, length of array,
	// and a list of each index in the array, with all variables separated by commas
	public String toString() {
		String header = "numCusts = " + numCusts + "\n" +
						"custArray.length = " + custArray.length + "\n";
		String body = ""; 
			
		for(int i = 0; i < numCusts; i++) {
			body = body + "\ncustArray[" + i +"] = "
						+ custArray[i].getSvgAcctNum() + ","
						+ custArray[i].getChkgAcctNum() + ","
						+ custArray[i].getCardNum() + ","
						+ custArray[i].getPinNum() + ","
						+ custArray[i].getLName() + ","
						+ custArray[i].getFName() + ","
						+ custArray[i].getAddress();
		}
		return header + body;
	}

	// Public helper methods:
	
	// Return requiring index
	
	// Return Customer info, require index
	//public String getCustByIndex(int index) {
	public Customer getCustByIndex(int index) {
		return custArray[index];
		//return custArray[index].toString();
	}
		
	// Return query on the number of Customers in data
	public int getNumCustomers() {
		return numCusts;
	}	
		
	// Return query on saving account number of a customer
	// within the array, require card number
	public String getSvgAcctNum(String cardNum) {
		int i = isIndex(cardNum);
		return custArray[i].getSvgAcctNum();
	}
		
	// Return query on checking account number of a customer
	// within the array, require card number
	public String getChkgAcctNum(String cardNum) {
		int i = isIndex(cardNum);
		return custArray[i].getChkgAcctNum();
	}
		
	// Return query on last name of a customer
	// within the array, require card number
	public String getLName(String cardNum) {
		int i = isIndex(cardNum);
		return custArray[i].getLName();
	}
		
	// Return query on first name of a customer
	// within the array, require card number
	public String getFName(String cardNum) {
		int i = isIndex(cardNum);
		return custArray[i].getFName();
	}
		
	// Return query on address of a customer
	// within the array, require card number
	public String getAddress(String cardNum) {
		int i = isIndex(cardNum);
		return custArray[i].getAddress();
	}
		
	//Return query on full information of a Customer
	// within the array, require card number
	public String getCustInfo(String cardNum) {
		int i = isIndex(cardNum);
		return custArray[i].toString();
	}
		
	//Filename must include path + filename
	//Set new sourceName for file that does not exist
	public void setSourceName(String filename) {
		sourceName = filename;
	}
		
	//End public helper methods
		
	//Public main methods

	// Add new Customer to customer array
	//or modifies pin, last name, first name, address of an existing account in the account array
	public void addOrModifyCustomer(String newSvgNum,String newChkgNum,String newCardNum,String newPin,
									String newLName,String newFName,String newAddress) {
				
	boolean contain = false;
				
		if(numCusts == custArray.length) {
			expandArray();
		}
			
		for(int i = 0; i < numCusts; i++) {
			if((custArray[i].getSvgAcctNum().equals(newSvgNum) || custArray[i].getChkgAcctNum().equals(newChkgNum))
							&& custArray[i].getCardNum().equals(newCardNum)) {
				
				custArray[i].setPinNum(newPin);
				custArray[i].setLName(newLName);
				custArray[i].setFName(newFName);
				custArray[i].setAddress(newAddress);
				modified = true;
				contain = true;
			}
		}
				
		if(!contain) {
			custArray[numCusts] = new Customer(newSvgNum,newChkgNum,newCardNum,newPin,
												newLName,newFName,newAddress);
			modified = true;
			numCusts++;
			sort();
		}
	}
		
	//Method to remove Customer from customer array
	public void removeCustomer(String cardNum) {
		int i = isIndex(cardNum);
		while(custArray[i+1] != null) {
			custArray[i] = custArray[i+1];
			i++;
		}
		numCusts--;
		modified = true;
	}

	//Load existing database file
	public void loadData(String filename) {
			
		sourceName = System.getProperty("user.dir") + "\\src\\" + filename;
			
		File file = new File(sourceName);
			
		try {
			Scanner scanner = new Scanner(file);

			while(scanner.hasNextLine()) {
				String data = scanner.nextLine();
					
				if(data.length() > 0) {
					int counter = 0;
					String[] splitData = data.split(",");
					String[] temp = new String [7];
						
					for(String split : splitData) {
						temp[counter] = split;
						counter++;
					}

					addOrModifyCustomer(temp[0],temp[1],temp[2],temp[3],temp[4],temp[5],temp[6]);
					modified = false;
				}
			}
			scanner.close();
		}
		
		catch(Exception e) {
			//JOptionPane.showMessageDialog(null, "Message: " + e);
			System.out.println("Message: " + e);
				
			/*for(int i = 0; i < dvdArray.length; i++) {
				System.out.println("dvdArray[" + i + "]: " + dvdArray[i]);
			}*/
		}
	}
		
	//Save file according to sourceName value
	//Create new file according to sourceName value if sourceName does not exist
	public void save() {
		if(!modified) {
			//System.out.println("Exited");
		}
			
		else {			
			try {
				FileWriter fWriter = new FileWriter(sourceName);
						
				for(int i = 0; i < numCusts; i++) {
					fWriter.write(custArray[i] + "\n");
				}
				fWriter.close();
			}
				
			catch(IOException e) {
				e.printStackTrace();
			}
			//System.out.println("File saved!");
		}
	}
		
	// Method to query customer array for all Customers by saving account number
	public String getCustsBySvgAcct(String svgAcctNum) {
		String foundCustomers = "";
					
		for (int i = 0; i < numCusts; i++) {
			if(custArray[i].getSvgAcctNum().equals(svgAcctNum)) {
				foundCustomers = foundCustomers + custArray[i].getLName() + "," 
									+ custArray[i].getFName() + ","
									+ custArray[i].getAddress() + "\n";
			}
		}
		return foundCustomers;
	}

	// Method to query customer array for all Customers by checking account number
	public String getCustsByChkgAcct(String chkgAcctNum) {
		String foundCustomers = "";
					
		for (int i = 0; i < numCusts; i++) {
			if(custArray[i].getChkgAcctNum().equals(chkgAcctNum)) {
							foundCustomers = foundCustomers + custArray[i].getLName() + "," 
									+ custArray[i].getFName() + ","
									+ custArray[i].getAddress() + "\n";
			}
		}
		return foundCustomers;
	}
		
	// Confirm if account number match with card number and pin
	public boolean isAccountBelongToCard(String inAcctNum,String inCardNum,String inPin) {
			
		if(!(isCard(inCardNum,inPin))) {
			int i = isIndex(inCardNum);
			if(custArray[i].getChkgAcctNum().equals(inAcctNum) || custArray[i].getSvgAcctNum().equals(inAcctNum)) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
		
	// Confirm if card and pin match
	public boolean isCard(String inCardNum,String inPin) {
		int i = isIndex(inCardNum);
		if(!(i < 0)) {
			if(!(custArray[i].getPinNum().compareTo(inPin) == 0)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
		
	//End main public methods

	// Additional private helper methods go here:
		
	// Expand array to accommodate more employees
	private void expandArray() {
		Customer[] temp = new Customer[custArray.length];
			
		for(int i = 0; i < custArray.length; i++) {
			temp[i] = custArray[i];
		}
		custArray = new Customer[(custArray.length+7)];
		for(int i = 0; i < custArray.length-7; i++) {
			custArray[i] = temp[i];
		}
	}

	//Check if Customer exist in the array, and return index
	private int isIndex(String numInfo) {
		int foundOn = -1;
		for (int i = 0; i < numCusts;i++) {
			if(custArray[i].getCardNum().equals(numInfo)) {
						//|| custArray[i].getChkgAcctNum().equals(numInfo)
						//|| custArray[i].getSvgAcctNum().equals(numInfo)) {
				foundOn = i;
			}
		}
		return foundOn;		
	}
		
	// Sort customer array by saving account number
	private void sort() {	
		for(int i = 0; i < numCusts-1; i++) {
			for(int j = i+1; j < numCusts; j++) {
				if(custArray[i].getSvgAcctNum().compareTo(custArray[j].getSvgAcctNum()) > 0) {
					Customer temp = custArray[i];
					custArray[i] = custArray[j];
					custArray[j] = temp;
				}
			}
		}
	}
}
