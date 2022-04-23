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
			// Return a string containing all the DVDs in the
			// order they are stored in the array along with
			// the values for numdvds and the length of the array.
			// Per assignment 1 instructions for proper format.
			/*
			String header = "numdvds = " + numdvds + "\n" +
							"dvdArray.length = " + dvdArray.length + "\n";
			String body = ""; 
			
			for(int i = 0; i < numdvds; i++) {
				body = body + "\ndvdArray[" + i +"] = "
						+ dvdArray[i].getTitle()
						+ "," + dvdArray[i].getRating()
						+ "," + dvdArray[i].getRunningTime() + "min\n";
			}
			return header + body;
			*/
			return null;
		}


		//Public helper methods:
		
		//Return query on the number of Employee in data
		public int getNumCustomers() {
			return numCusts;
		}	
		//Return query on username of an employee
		public int getSvgAcctNum(int index) {
			return custArray[index].getSvgAcctNum();
		}
		//Return query on position of an employee
		public int getChkgAcctNum(int index) {
			return custArray[index].getChkgAcctNum();
		}
		//Return query on position of an employee
		public int getCardNum(int index) {
			return custArray[index].getCardNum();
		}
		public int getPinNum(int index) {
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
		public void addOrModifyCustomer(int newSvgNum,int newChkgNum,int newCardNum,int newPin,
											String newLName,String newFName,String newAddress) {
			
			//if((rating.equals("PG") || rating.equals("PG-13") || rating.equals("R")) && Integer.parseInt(runningTime) > 0) {
				
				boolean contain = false;
				
				if(numCusts == custArray.length) {
					expandArray();
				}
			
				for(int i = 0; i < numCusts; i++) {
					if(custArray[i].getSvgAcctNum() == newSvgNum || custArray[i].getChkgAcctNum() == newChkgNum) {
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
		public String getCustsBySvgAcct(int svgAcctNum) {
			String foundCustomers = "";
			
			for (int i = 0; i < numCusts; i++) {
				if(custArray[i].getSvgAcctNum() == svgAcctNum) {
					foundCustomers = foundCustomers + custArray[i].getLName() + "," 
							+ custArray[i].getFName() + ","
							+ custArray[i].getAddress() + "\n";
				}
			}
			return foundCustomers;
		}

		//Method to query data array for all Customers by checking account number
		public String getCustsByChkgAcct(int chkgAcctNum) {
			String foundCustomers = "";
			
			for (int i = 0; i < numCusts; i++) {
				if(custArray[i].getChkgAcctNum() == chkgAcctNum) {
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

						addOrModifyCustomer(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),
								Integer.parseInt(temp[2]),Integer.parseInt(temp[3]),temp[4],temp[5],temp[6]);
						modified = false;
					}
				}
				scanner.close();
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Message: " + e);
				//System.out.println("Message: " + e);
				
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
					if(custArray[i].getSvgAcctNum() > custArray[j].getSvgAcctNum()) {
						Customer temp = custArray[i];
						custArray[i] = custArray[j];
						custArray[j] = temp;
					}
				}
			}
		}
}
