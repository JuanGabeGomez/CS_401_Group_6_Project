import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//import javax.swing.JOptionPane;

public class EmployeesData {
	// Data fields

	/** The current number of Employees in the array */
	private int numEmps;
		
	/** The array to contain the Employees */
	private Employee[] empArray;
		
	/** The name of the data file that contains Employee data */
	private String sourceName;
		
	/** Boolean flag to indicate whether the EmployeeData was
		modified since it was last saved. */
	private boolean modified;
	
	// Additional variables
	
	// Set the height level of authorization comparable to manager or higher
	private String highLvlAuth = "2";
		
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
		
	// Return a string with number of Employee in the array, length of array,
	// and a list of each index in the array, with all variables separated by commas
	public String toString() {
		String body = ""; 
			
		String header = "numEmps = " + numEmps + "\n" +
					"empArray.length = " + empArray.length + "\n";
			
		for(int i = 0; i < numEmps; i++) {
			body = body + "\nempArray[" + i +"] = "
						+ empArray[i].getUsername() + "," 
						+ empArray[i].getPassword() + "," 
						+ empArray[i].getPosition() + "," 
						+ empArray[i].getAuthLevel();
		}
		return header + body;
	}

	// Public helper methods:
	
	public Employee getEmpByInd(int index) {
		return empArray[index];
	}
		
	// Return query on the number of Employee in the array
	public int getNumEmployee() {
		return numEmps;
	}
		
	// Return query on username of an employee, require index in the array
	public String getUsername(int index) {
		return empArray[index].getUsername();
	}
		
	// Return query on position of an employee, require username
	public String getPosition(String username) {
		int i = isIndex(username);
		return empArray[i].getPosition();
	}
		
	// Return query on authorization level of an employee, require username
	public int getAuthLevel(String username) {
		int i = isIndex(username);
		return Integer.parseInt(empArray[i].getAuthLevel());
	}
		
	// Return a String of sharable information of an employee, require username
	public String getEmpInfo(String username) {
		int i = isIndex(username);
		String info = "Username: " + username
					+ "\nPosition: " + empArray[i].getPosition()
					+ "\nAuthorization Level: " + empArray[i].getAuthLevel();
		return info;
	}
		
	// Filename must include path + filename
	// Set new sourceName for file that does not exist
	public void setSourceName(String filename) {
		sourceName = filename;
	}
		
	// End public helper methods
		
	// Public main methods

	// Add new Employee to employee array
	// or modifies password, position, authorization level of an existing account in the account array
	public void addOrModifyEmployee(String username,String password,String position,String authLevel) {
				
		if(numEmps == empArray.length) {
			expandArray();
		}
				
		int i = isIndex(username);
				
		if(!(i < 0)) {
			empArray[i].setPassword(password);
			empArray[i].setPosition(position);
			empArray[i].setAuthLevel(authLevel);
			modified = true;
		}
				
		if(i < 0) {
			empArray[numEmps] = new Employee(username,password,position,authLevel);
			modified = true;
			numEmps++;
			sort();
		}
	}
		
	// Method to remove an Employee from employee array
	public void removeEmployee(String username) {
		int i = isIndex(username);
		while(empArray[i+1] != null) {
			empArray[i] = empArray[i+1];
			i++;
		}
		numEmps--;
		modified = true;
	}
		
	// Return a query of all Employees by position, require type of position
	public String getEmployeesByPosition(String position) {
		String foundEmployees = "";
			
		for (int i = 0; i < numEmps; i++) {
			if(empArray[i].getPosition().equals(position)) {
				foundEmployees = foundEmployees + empArray[i].getUsername() + "\n";
			}
		}
		return foundEmployees;
	}

	// Load existing database file
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
		
	// Save file according to sourceName value
	// Create new file according to sourceName value if sourceName does not exist
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
				e.printStackTrace();
			}
			//System.out.println("File saved!");
		}
	}
		
	// Confirm if username and password match
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
		
	// Return true if authorization is level 2, otherwise return false
	public boolean isAuthorized(String username,String password) {
		int i = isIndex(username);
		if(empArray[i].getPassword().equals(password) && empArray[i].getAuthLevel().equals(highLvlAuth)) {
			return true;
		}
		else {
			return false;
		}
	}
		
	// End main public methods

	// Additional private helper methods go here:
		
	// Expand array to accommodate more employees
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
		
	// Return index if Employee exist in the employee array, require username
	private int isIndex(String username) {
		int foundOn = -1;
		for (int i = 0; i < numEmps;i++) {
			if(empArray[i].getUsername().equals(username)) {
				foundOn = i;
			}
		}
		return foundOn;		
	}
		
	// Alphabetically sort employee array by username
	private void sort() {
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
