import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class CustomersData {
	// Data fields
	
		/** The current number of DVDs in the array */
		private int numCusts;
		
		/** The array to contain the DVDs */
		private Customer[] custArray;
		
		/** The name of the data file that contains dvd data */
		private String sourceName;
		
		/** Boolean flag to indicate whether the DVD collection was
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


		//Public helper methods:
		
		//Return query on the number of Employee in data
		public int getNumCustomers() {
			return numCusts;
		}	
		/*
		//Return query on position of an employee
		public String getCardNum(int index) {
			return custArray[index].getCardNum();
		}
		public String getPinNum(int index) {
			return custArray[index].getPinNum();
		}
		public String getLName(int index) {
			return custArray[index].getLName();
		}
		public String getFName(int index) {
			return custArray[index].getFName();
		}
		public String getAddress(int index) {
			return custArray[index].getAddress();
		}
		*/
		//Return query on username of an employee
		public String getSvgAcctNum(String cardNum) {
			int i = isIndex(cardNum);
			return custArray[i].getSvgAcctNum();
		}
		//Return query on checking account number 
		public String getChkgAcctNum(String cardNum) {
			int i = isIndex(cardNum);
			return custArray[i].getChkgAcctNum();
		}
		public String getCustInfo(String acctNum) {
			int i = isIndex(acctNum);
			return custArray[i].toString();
		}
		/*
		//String together sharable information of an employee
		public String getInfo(int index) {
			String info = "Saving account#: " + custArray[index].getSvgAcctNum()
					+ "\nChecking account#: " + empArray[index].getPosition()
					+ "\nAuthorization Level: " + empArray[index].getAuthLevel();
			return info;
		}
		*/
		
		//Filename must include path + filename
		//Set new sourceName for file that does not exist
		public void setSourceName(String filename) {
			sourceName = filename;
		}
		
		//End public helper methods
		
		//Public main methods

		//Add new DVD to collection array
		//or modifies rating and/or runtime of an existing DVD in the collection array
		public void addOrModifyCustomer(String newSvgNum,String newChkgNum,String newCardNum,String newPin,
											String newLName,String newFName,String newAddress) {
			
			//if((rating.equals("PG") || rating.equals("PG-13") || rating.equals("R")) && Integer.parseInt(runningTime) > 0) {
				
				boolean contain = false;
				
				if(numCusts == custArray.length) {
					expandArray();
				}
			
				for(int i = 0; i < numCusts; i++) {
					if(custArray[i].getSvgAcctNum().equals(newSvgNum) || custArray[i].getChkgAcctNum().equals(newChkgNum)) {
						custArray[i].setCardNum(newCardNum);
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
			//}
		}
		
		//Method to remove employee from data array
		public void removeCustomer(int index) {
			int i = index;
			while(custArray[i+1] != null) {
				custArray[i] = custArray[i+1];
				i++;
			}
			numCusts--;
			modified = true;
		}
		
		//Method to query data array for all Customers by saving account number
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

		//Method to query data array for all Customers by checking account number
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

		//Load existing database file
		//In the case of class assignment all filename should include *.txt extension
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
					e.printStackTrace();		//GUI should catch error before getting to this point
				}

				//System.out.println("File saved!");
			}

		}
		
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
		
		public boolean isCard(String inCardNum,String inPin) {
			int i = isIndex(inCardNum);
			if(!(i < 0)) {
				if(custArray[i].getPinNum() == inPin) {
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
		
		//Expand array to accommodate more employees
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

		/*private boolean isCard(int inCardNum,int inPin) {
			int i = isIndex(inCardNum);
			if(!(i < 0)) {
				if(custArray[i].getPinNum() == inPin) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}*/
		/*
		private int indexBySvgNum(String svgNum) {
			int foundOn = -1;
			for (int i = 0; i < numCusts;i++) {
				if(custArray[i].getSvgAcctNum().equals(svgNum)) {
					foundOn = i;
				}
			}
			
			return foundOn;	
		}
		private int indexByChkgNum(String chkgNum) {
			int foundOn = -1;
			for (int i = 0; i < numCusts;i++) {
				if(custArray[i].getChkgAcctNum().equals(chkgNum)) {
					foundOn = i;
				}
			}
			
			return foundOn;	
		}
		*/
		private int isIndex(String numInfo) {
			int foundOn = -1;
			for (int i = 0; i < numCusts;i++) {
				if(custArray[i].getCardNum().equals(numInfo) 
						|| custArray[i].getChkgAcctNum().equals(numInfo)
						|| custArray[i].getSvgAcctNum().equals(numInfo)) {
					foundOn = i;
				}
			}
			
			return foundOn;		
		}
		
		
		/*
		//Check if Employees exist in data
		private boolean isContain(int i, int acctNum) {
			if(empArray[i].getUsername().equals(username.trim())) {
				return true;
			}
					
			return false;
		}
		*/
		
		//Sort data array saving account number
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
