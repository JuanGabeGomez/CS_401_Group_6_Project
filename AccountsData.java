import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//import javax.swing.JOptionPane;

public class AccountsData {
	// Data fields
	
	/** The current number of accounts in the array */
	private int numAccts;
		
	/** The array to contain the accounts */
	private Account[] acctArray;
		
	/** The name of the data file that contains account data */
	private String sourceName;
		
	/** Boolean flag to indicate whether the AccountData was
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
		
	// Return a string with number of Accounts in the array, length of array,
	// and a list of each index in the array, with all variables separated by commas
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

	// Public helper methods:
		
	// Return query on the number of Employee in data
	public int getNumAcct() {
		return numAccts;
	}
	//public String getStatusByIndex(int index) {
	public Account getStatusByIndex(int index) {
		//return acctArray[index].getStatus();
		return acctArray[index];
	}
	
	// Return requiring account number
	
	// Return query on type of an account, require account number
	public String getType(String acctNum) {
		int i = isIndex(acctNum);
		return acctArray[i].getType();
	}
		
	// Return query on status of an account, require account number
	public String getStatus(String acctNum) {
		int i = isIndex(acctNum);
		return acctArray[i].getStatus();	
	}
		
	// Return query on balance of an account, require account number
	public String getBalance(String acctNum) {
		int i = isIndex(acctNum);
		return acctArray[i].getBalance();
	}
		
	// Return a String of specific account, require account number
	public String getAcctInfo(String acctNum) {
		int i = isIndex(acctNum);
		return acctArray[i].toString();
	}
		
	// Set the status of an account, require account number and new status to set
	public void setStatus(String acctNum,String inStatus) {
		int i = isIndex(acctNum);
		acctArray[i].setStatus(inStatus);
		modified = true;
	}
		
	// Set the balance of an account, require account number and the new balance to set
	public void setBalance(String acctNum,String inBal) {
		int i = isIndex(acctNum);
		acctArray[i].setBalance(inBal);
		modified = true;
	}
		
	// Filename must include path + filename
	// Set new sourceName for file that does not exist
	public void setSourceName(String filename) {
		sourceName = filename;
	}
		
	// End public helper methods
		
	// Public main methods

	// Add new Account to account array
	// or modifies status and/or balance of an existing account in the account array
	public void addOrModifyAccount(String inAcctNum,String inType,String inStatus,String inBal) {
				
		if(numAccts == acctArray.length) {
			expandArray();
		}
			
		int i = isIndex(inAcctNum);
				
		if(!(i < 0)) {
			acctArray[i].setStatus(inStatus);
			acctArray[i].setBalance(inBal);
			modified = true;
		}
		if(i < 0) {
			acctArray[numAccts] = new Account(inAcctNum,inType,inStatus,inBal);
			modified = true;
			numAccts++;
			sort();
		}
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
				//System.out.println(sourceName);
				FileWriter fWriter = new FileWriter(sourceName);
						
				for(int i = 0; i < numAccts; i++) {
					//System.out.println(acctArray[i].getStatus());
					fWriter.write(acctArray[i] + "\n");
				}
				fWriter.close();
			}
				
			catch(IOException e) {
				e.printStackTrace();
			}
			//System.out.println("File saved!");
		}
		}
		
	public String getAccount(String acctNum) {
		if(!"in use".equalsIgnoreCase(getStatus(acctNum))) {
			setStatus(acctNum,"in use");
			save();
			return getAcctInfo(acctNum);
		}
		else {
			return "Account is in use";
		}
	}

	//End main public methods

	// Additional private helper methods go here:
		
	//Expand array to accommodate more accounts
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
		
	//Check if Account exist in the array, and return index
	private int isIndex(String acctNum) {
		int foundOn = -1;
		for (int i = 0; i < numAccts;i++) {
			if(acctArray[i].getAcctNum().equals(acctNum)) {
				foundOn = i;
			}
		}	
		return foundOn;		
	}
		
	//Alphabetically/Numerically sort array
	private void sort() {
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
