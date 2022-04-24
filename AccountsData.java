import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class AccountsData {
	// Data fields
	
		/** The current number of DVDs in the array */
		private int numAccts;
		
		/** The array to contain the DVDs */
		private Account[] acctArray;
		
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
		public AccountsData() {
			this.numAccts = 0;
			this.acctArray = new Account[7];
		}
		
		public String toString() {
			String header = "numAccts = " + numAccts + "\n" +
					"acctArray.length = " + acctArray.length + "\n";
			String body = ""; 
			
			for(int i = 0; i < numAccts; i++) {
				body = body + "\nacctArray[" + i +"] = "
						+ acctArray[i].getAcctNum() + "," 
						+ acctArray[i].getType() + "," 
						+ acctArray[i].getStatus() + ","
						+ acctArray[i].getBalance();
			}
			return header + body;
		}


		//Public helper methods:
		
		//Return query on the number of Employee in data
		public int getNumAcct() {
			return numAccts;
		}
		
		//Return query on username of an employee
		public String getType(int index) {
			return acctArray[index].getType();
		}
		
		//Return query on position of an employee
		public String getStatus(String acctNum) {
			int i = isIndex(acctNum);
			return acctArray[i].getStatus();	
		}
		
		//Return query on authorization level of an employee
		public String getBalance(String acctNum) {
			int i = isIndex(acctNum);
			return acctArray[i].getBalance();
		}
		
		public String getAcctInfo(String acctNum) {
			int i = isIndex(acctNum);
			return acctArray[i].toString();
		}
		
		public void setStatus(String acctNum,String inStatus) {
			int i = isIndex(acctNum);
			acctArray[i].setStatus(inStatus);
			modified = true;
		}
		
		public void setBalance(String acctNum,String inBal) {
			int i = isIndex(acctNum);
			acctArray[i].setBalance(inBal);
			modified = true;
		}
		
		/*
		//String together sharable information of an employee
		public String getInfo(int index) {
			String info = "Username: " + empArray[index].getUsername()
					+ "\nPosition: " + empArray[index].getPosition()
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
		public void addOrModifyAccount(String inAcctNum,String inType,String inStatus,String inBal) {
			
			//if((rating.equals("PG") || rating.equals("PG-13") || rating.equals("R")) && Integer.parseInt(runningTime) > 0) {
				
				//boolean contain = false;
				
				if(numAccts == acctArray.length) {
					expandArray();
				}
			
				int i = isIndex(inAcctNum);
				
				if(!(i < 0)) {
					acctArray[i].setStatus(inStatus);
					acctArray[i].setBalance(inBal);
					modified = true;
					//contain = true;
				}
				/*
				for(int i = 0; i < numAccts; i++) {
					if(acctArray[i].getAcctNum().equals(inAcctNum)) {
						acctArray[i].setStatus(inStatus);
						acctArray[i].setBalance(inBal);
						modified = true;
						contain = true;
					}
				}
				*/
				
				//if(!contain) {
				if(i < 0) {
					acctArray[numAccts] = new Account(inAcctNum,inType,inStatus,inBal);
					modified = true;
					numAccts++;
					sort();
				}
			//}
		}
		
		//Method to remove employee from data array
		public void removeAccount(String inAcctNum) {
			int i = isIndex(inAcctNum);
			if(!(i < 0)) {
				while(acctArray[i+1] != null) {
					acctArray[i] = acctArray[i+1];
					i++;
				}
				numAccts--;
				modified = true;
			}
		}

		/*
		//Method to query data array for all Employees by position
		public String getEmployeesByPosition(String position) {
			String foundEmployees = "";
			
			for (int i = 0; i < numEmps; i++) {
				if(empArray[i].getPosition().equals(position)) {
					foundEmployees = foundEmployees + empArray[i].getUsername() + "\n";
				}
			}
			return foundEmployees;
		}
		*/
		/*
		//Total runtime of every DVDs in collection array
		public int getTotalRunningTime() {
			int total = 0;
			
			for(int i = 0; i < numdvds; i++) {
				total += dvdArray[i].getRunningTime();
			}
			return total;
		}
		*/

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
						String[] temp = new String [4];
						
						for(String split : splitData) {
							temp[counter] = split;
							counter++;
						}

						addOrModifyAccount(temp[0],temp[1],temp[2],temp[3]);
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
					System.out.println(sourceName);
						FileWriter fWriter = new FileWriter(sourceName);
						
						for(int i = 0; i < numAccts; i++) {
							System.out.println(acctArray[i].getStatus());
							fWriter.write(acctArray[i] + "\n");
						}
						fWriter.close();
				}
				
				catch(IOException e) {
					e.printStackTrace();		//GUI should catch error before getting to this point
				}

				//System.out.println("File saved!");
			}

		}
		
		public String getAccount(String acctNum) {
			
			return "";
		}
		//End main public methods

		// Additional private helper methods go here:
		
		//Expand array to accommodate more employees
		private void expandArray() {
			Account[] temp = new Account[acctArray.length];
			
			for(int i = 0; i < acctArray.length; i++) {
				temp[i] = acctArray[i];
			}
			acctArray = new Account[(acctArray.length+7)];
			for(int i = 0; i < acctArray.length-7; i++) {
				acctArray[i] = temp[i];
			}
		}
		
		//Check if Employees exist in data
		private int isIndex(String acctNum) {
			int foundOn = -1;
			for (int i = 0; i < numAccts;i++) {
				if(acctArray[i].getAcctNum().equals(acctNum)) {
					foundOn = i;
				}
			}
			
			return foundOn;		
		}
		
		//Alphabetically sort data array
		private void sort() {	//Code reference: tutorialspoint.com/How-to-sort-a-String-array-in-Java
			for(int i = 0; i < numAccts-1; i++) {
				for(int j = i+1; j < numAccts; j++) {
					if(acctArray[i].getAcctNum().compareTo(acctArray[j].getAcctNum())>0) {
						Account temp = acctArray[i];
						acctArray[i] = acctArray[j];
						acctArray[j] = temp;
					}
				}
			}
		}
}
