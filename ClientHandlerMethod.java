import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ClientHandlerMethod {
	//private final Socket clientSocket;
			private int authLevel = 0;
			
			// Database
			private EmployeesData employee = new EmployeesData();
			private CustomersData customer = new CustomersData();
			private AccountsData account = new AccountsData();

			// Constructor
			public ClientHandlerMethod(Message message) {
				String bankName = "Bank" + message.getBankNum();
		    	employee.loadData(bankName + "-Employees.txt");
		        customer.loadData(bankName + "-Customers.txt");
		        account.loadData(bankName + "-Accounts.txt");
			}

			// Private client handler methods
			
			// Handles request from desktop clients
			public Message desktopHandler(Message dtMsg) {
				//System.out.println("Initial authorization level: " + authLevel);
				
				// Listen for type login
				if("login".equalsIgnoreCase(dtMsg.getType())) {
					
					//Login by desktop user requires username and password
					int numInd = 2;
					
					int empUnInd = 0;
					int empPwInd = 1;

					// Split received text message into array for processing
					int counter = 0;
					String[] splitText = dtMsg.getText().split(",");
					String[] signIn = new String [numInd];
					
					for(String a : splitText) {
						signIn[counter] = a;
						counter++;
					}
		        	
					// If username and password does not match, status return "failed"
					// else return status "success"
					if(!employee.isEmployee(signIn[empUnInd],signIn[empPwInd])) {
						dtMsg.setStatus("failed");
					}
					else {
						authLevel = employee.getAuthLevel(signIn[empUnInd]);
						dtMsg.setStatus("success");
					}       
			        return dtMsg;
			    }
				
				// Listen for type inquiry
				if("inquiry".equalsIgnoreCase(dtMsg.getType())) {
					
					//Desktop inquiry requires account number, card number, and pin
					int numInd = 3;
					
					int acctInd = 0;
					int cardInd = 1;
					int pinInd = 2;
					
					// Split received text message into array for processing
					int counter = 0;
					String[] splitText = dtMsg.getText().split(",");
					String[] query = new String [numInd];
					
					for(String a : splitText) {
						query[counter] = a;
						counter++;
					}

					/* If account number, card number, and pin matches, change status to "success",
					 * update text message to include customer info,account info
					 * If any of the field does not match return status "failed
					 * update text message to include "Information provided does not match"
					 */
					if(customer.isAccountBelongToCard(query[acctInd],query[cardInd],query[pinInd])) {
						dtMsg.setStatus("success");
						dtMsg.setText(customer.getCustInfo(query[cardInd]) + "," + account.getAccount(query[acctInd]));
					}
					else {
						dtMsg.setStatus("failed");
						dtMsg.setText("Information provided does not match");
					}
					return dtMsg;
				}
				
				// Listen for type authorization
				if("authorization".equalsIgnoreCase(dtMsg.getType())) {
					
					// Desktop authorization requires username and password
					int numInd = 2;
					
					int empUnInd = 0;
					int empPwInd = 1;
					
					// Split received text message into array for processing
					int counter = 0;
					String[] splitText = dtMsg.getText().split(",");
					String[] signIn = new String [numInd];
					
					for(String a : splitText) {
						signIn[counter] = a;
						counter++;
					}
		        	
					// Requires employee username and password, if does not match return "not authorized"
					// else return "authorized"
					if(!employee.isAuthorized(signIn[empUnInd],signIn[empPwInd])) {
						dtMsg.setStatus("not authorized");
					}
					else {
						dtMsg.setStatus("authorized");
					}
			        return dtMsg;
				}
				
				// Listen for type update
				if("update".equalsIgnoreCase(dtMsg.getType())) {
					
					/* 
					 * Desktop update requires customer object and account object
					 * Customer.toString() consist of: saving account number, checking account number, card number,
					 * pin number, last name, first name, address
					 * Account.toString() consist of: account number, type of account, account status, account balance
					 */
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
					
					// Split received text message into array for processing
					int counter = 0;
					String[] splitText = dtMsg.getText().split(",");
					String[] recInfo = null;
					
					recInfo = new String [numInd];
						
					for(String a : splitText) {
						recInfo[counter] = a;
						counter++;
					}

					// Future consideration: if statement with status "new"
					// Change status from "in use" to "not used", so that the account is open to be updated
					if(recInfo[acctStatInd].equals("in use")) {
						recInfo[acctStatInd] = "not used";
					}
					
					/*
					// Future consideration: limiting change by authorization level
					if(authLevel >= 1) {
						
					}
					if(authLevel == 0) {
						
					}
					*/
					
					// Add or modify Customer in the customer collective
					customer.addOrModifyCustomer(recInfo[svgAcctInd],recInfo[chkgAcctInd],recInfo[cardInd],
							recInfo[pinInd],recInfo[lNameInd],recInfo[fNameInd],recInfo[addressInd]);
					
					// Add or modify Account in the account collective
					account.addOrModifyAccount(recInfo[acctNumInd],recInfo[acctTypeInd],recInfo[acctStatInd],
							recInfo[acctBalInd]);
					
					customer.save();
					account.save();
					
					dtMsg.setStatus("success");
					dtMsg.setText("Update completed");
					return dtMsg;

				}
				
				// Listen for type logout
				if("logout".equalsIgnoreCase(dtMsg.getType())){
					dtMsg.setStatus("success");
					return dtMsg;
				}
				
				// Return message as is due to no type matching
				else {
					return dtMsg;
				}
			}
			
			// Handles request from ATM clients
			public Message atmHandler(Message atmMsg) {
				
				// Listen for ATM login type
				if("login".equalsIgnoreCase(atmMsg.getType())) {
					 
					// ATM login requires card number and pin
					int numInd = 2;
					
					int cardInd = 0;
					int pinInd = 1;
					
					// Split received text message into array for processing
					int counter = 0;
					String[] splitText = atmMsg.getText().split(",");
					String[] querry = null;
					
					querry = new String [numInd];
						
					for(String a : splitText) {
						querry[counter] = a;
						counter++;
					}
						
					// Authenticate card and return account info if success, else return failed
					if(!(customer.isCard(querry[cardInd],querry[pinInd]))) {
					String chkgAcct = customer.getChkgAcctNum(querry[cardInd]);
					if(!"in use".equalsIgnoreCase(account.getStatus(chkgAcct))){
						atmMsg.setStatus("success");
						atmMsg.setText(account.getAccount(chkgAcct));
					}
					else {
						atmMsg.setStatus("in use");
						atmMsg.setText("Account is in use");
					}
				}
					else {
						atmMsg.setStatus("failed");
						atmMsg.setText("Incorrect pin");
					}
				}
				
				// Listen for ATM logout type
				if("logout".equalsIgnoreCase(atmMsg.getType())) {
					
					// ATM logout requires account object
					int numInd = 4;
					
					int acctInd = 0;
					int acctTypeInd = 1;
					int acctStatInd = 2;
					int acctBalInd = 3;
					
					// Split received text message into array for processing
					int counter = 0;
					String[] splitText = atmMsg.getText().split(",");
					String[] recInfo = null;
					
					recInfo = new String [numInd];
						
					for(String a : splitText) {
						recInfo[counter] = a;
						counter++;
					}

					// Ready account status to be reuse
					if(recInfo[acctStatInd].equals("in use")) {
						recInfo[acctStatInd] = "not used";
					}

					// Add or modifies Account in the collective
					account.addOrModifyAccount(recInfo[acctInd],recInfo[acctTypeInd],
							recInfo[acctStatInd],twoDecimal(recInfo[acctBalInd]));
						
					account.save();
						
					atmMsg.setStatus("success");
					atmMsg.setText("Have a good day");

				}
				return atmMsg;
			}
			
			//private helper methods
			
			private String twoDecimal(String number) {
				double temp = Double.parseDouble(number);
				DecimalFormat formatDecimal = new DecimalFormat("0.00");
				formatDecimal.setRoundingMode(RoundingMode.HALF_UP);
				String adjusted = formatDecimal.format(temp);
				
				return adjusted;
			}
			
			/*
			// Get account info in account database
			private String getAccount(String acctNum) {
				if(!"in use".equalsIgnoreCase(account.getStatus(acctNum))) {
					account.setStatus(acctNum,"in use");
					account.save();
					return account.getAcctInfo(acctNum);
				}
				else {
					return "Account is in use";
				}
			}
			*/
			
			/*
			// Future consideration for converting currency
			private String dollarToYen(float dollar) {
				
				return "";
			}
			private String yenToDollar(float yen) {
				
				return "";
			}
			*/
}
