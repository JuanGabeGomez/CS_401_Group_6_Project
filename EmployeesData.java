import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class EmployeesData {
	// Data fields
	
	private String authorization = "0";
	/** The current number of DVDs in the array */
	private int numEmps;
		
	/** The array to contain the DVDs */
	private Employee[] empArray;
		
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
		public EmployeesData() {
			numEmps = 0;
			empArray = new Employee[7];
		}
		
		public String toString() {
			String body = ""; 
			
			for(int i = 0; i < numEmps; i++) {
				body = body + "\nempArray[" + i +"] = "
						+ empArray[i].getUsername() + "," 
						+ empArray[i].getPassword() + "," 
						+ empArray[i].getPosition() + "," 
						+ empArray[i].getAuthLevel();
			}
			return body;
			
			
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
			//return null;
		}


		//Public helper methods:
		
		//Return query on the number of Employee in data
		public int getNumEmployee() {
			return numEmps;
		}
		
		//Return query on username of an employee
		public String getUsername(int index) {
			return empArray[index].getUsername();
		}
		
		//Return query on position of an employee
		public String getPosition(int index) {
			return empArray[index].getPosition();
		}
		
		//Return query on authorization level of an employee
		public String getAuthLevel(int index) {
			return empArray[index].getAuthLevel();
		}
		
		//String together sharable information of an employee
		public String getInfo(int index) {
			String info = "Username: " + empArray[index].getUsername()
					+ "\nPosition: " + empArray[index].getPosition()
					+ "\nAuthorization Level: " + empArray[index].getAuthLevel();
			return info;
		}
		
		//Filename must include path + filename
		//Set new sourceName for file that does not exist
		public void setSourceName(String filename) {
			sourceName = filename;
		}
		
		//End public helper methods
		
		//Public main methods

		//Add new DVD to collection array
		//or modifies rating and/or runtime of an existing DVD in the collection array
		public void addOrModifyEmployee(String username,String password,String position,String authLevel) {
			//System.out.println("what: " + username + "," + password);
			//if((rating.equals("PG") || rating.equals("PG-13") || rating.equals("R")) && Integer.parseInt(runningTime) > 0) {
				
				boolean contain = false;
				
				if(numEmps == empArray.length) {
					expandArray();
				}
				
				int i = isIndex(username);
				
				if(!(i < 0)) {
					empArray[i].setPassword(password);
					empArray[i].setPosition(position);
					empArray[i].setAuthLevel(authLevel);
					modified = true;
					//contain = true;
				}
				
				/*
				for(int i = 0; i < numEmps; i++) {
					if(isContain(i, username) == true) {
						System.out.println("here?");
						empArray[i].setPassword(password);
						empArray[i].setPosition(position);
						empArray[i].setAuthLevel(authLevel);
						modified = true;
						contain = true;
					}
				}
				*/
				
				//if(!contain) {
				if(i < 0) {
					//System.out.println("there");
					empArray[numEmps] = new Employee(username,password,position,authLevel);
					/*System.out.println("there" + numEmps + ": " + empArray[numEmps].getUsername() + ","
						*	+ empArray[numEmps].getPassword() + ","
						*	+ empArray[numEmps].getPosition() + ","
						*	+ empArray[numEmps].getAuthLevel());
						*/
					modified = true;
					numEmps++;
					sort();
				}
		}
		
		//Method to remove employee from data array
		public void removeEmployee(String username) {
			int i = isIndex(username);
			while(empArray[i+1] != null) {
				empArray[i] = empArray[i+1];
				i++;
			}
			numEmps--;
			modified = true;
		}
		
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

						addOrModifyEmployee(temp[0],temp[1],temp[2],temp[3]);
						modified = false;
					}
				}
				scanner.close();
			}
			catch(Exception e) {
				//JOptionPane.showMessageDialog(null, "Message: " + e);
				System.out.println("Message: " + e);
				
				for(int i = 0; i < empArray.length; i++) {
					System.out.println("empArray[" + i + "]: " + empArray[i]);
				}
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
						
						for(int i = 0; i < numEmps; i++) {
							fWriter.write(empArray[i] + "\n");
						}
						fWriter.close();
				}
				
				catch(IOException e) {
					e.printStackTrace();		//GUI should catch error before getting to this point
				}

				//System.out.println("File saved!");
			}

		}
		
		public boolean isEmployee(String username,String password) {
			int i = isIndex(username);
			if(!(i < 0)) {
				if(empArray[i].getPassword().equals(password)) {
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
		
		public boolean isAuthorized(String username,String password) {
			int i = isIndex(username);
			if(empArray[i].getPassword().equals(password) && empArray[i].getAuthLevel().equals(authorization)) {
				//System.out.println("True: " + empArray[i].getUsername() + empArray[i].getPassword() + empArray[i].getAuthLevel());
				return true;
			}
			else {
				//System.out.println("False: " + empArray[i].getUsername() + empArray[i].getPassword() + empArray[i].getAuthLevel());
				return false;
			}
		}
		
		//End main public methods

		// Additional private helper methods go here:
		
		//Expand array to accommodate more employees
		private void expandArray() {
			Employee[] temp = new Employee[empArray.length];
			
			for(int i = 0; i < empArray.length; i++) {
				temp[i] = empArray[i];
			}
			empArray = new Employee[(empArray.length+7)];
			for(int i = 0; i < empArray.length-7; i++) {
				empArray[i] = temp[i];
			}
		}
		
		//Check if Employees exist in data
		private int isIndex(String username) {
			int foundOn = -1;
			for (int i = 0; i < numEmps;i++) {
				if(empArray[i].getUsername().equals(username)) {
					foundOn = i;
				}
			}
			
			return foundOn;		
		}
		
		/*
		private boolean isContain(int i, String username) {
			if(empArray[i].getUsername().equals(username.trim())) {
				return true;
			}
					
			return false;
		}
		*/
		
		//Alphabetically sort data array
		private void sort() {	//Code reference: tutorialspoint.com/How-to-sort-a-String-array-in-Java
			for(int i = 0; i < numEmps-1; i++) {
				for(int j = i+1; j < numEmps; j++) {
					if(empArray[i].getUsername().compareTo(empArray[j].getUsername())>0) {
						Employee temp = empArray[i];
						empArray[i] = empArray[j];
						empArray[j] = temp;
					}
				}
			}
		}
}
