
public class Employee {
	protected String username;
	protected String password;
	protected String position;
	protected String authLevel; 

    //Constructor
    public Employee(String newUsername,String newPassword,String newPosition,String newAuthLevel) {
    	setUsername(newUsername);
        setPassword(newPassword);
        setPosition(newPosition);
        setAuthLevel(newAuthLevel);
    }
    
    public String toString() {
    	String employee = "";
    	employee = getUsername() + ","
    			+ getPassword() + ","
    			+ getPosition() + ","
    			+ getAuthLevel() + ",";
    	return employee;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public String getPosition() {
    	return position;
    }
    
    public String getAuthLevel() {
    	return authLevel;
    }
    
    private void setUsername(String newUsername) {
    	this.username = newUsername;
    }
    
    public void setPassword(String newPassword) {
    	this.password = newPassword;
    }
    
    public void setPosition(String newPosition) {
    	this.position = newPosition;
    }
    
    public void setAuthLevel(String newAuthLevel) {
    	this.authLevel = newAuthLevel;
    }
}
