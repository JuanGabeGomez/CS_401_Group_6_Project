/*
 * Majority of the codes are from Threaded Sockets and Object Over Socket code
 * provided in class by the professor.
 */
import java.io.*;
import java.net.*;

// Server class
class Server {
	
	public static void main(String[] args)
	{
		ServerSocket server = null;

		try {

			// server is listening on port 1234
			server = new ServerSocket(1234);
			server.setReuseAddress(true);

			// running infinite loop for getting
			// client request
			while (true) {

				// socket object to receive incoming client
				// requests
				Socket client = server.accept();

				// Displaying that new client is connected
				// to server
				System.out.println("New client connected"
								+ client.getInetAddress()
										.getHostAddress());

				// create a new thread object
				ClientHandler clientSock
					= new ClientHandler(client);

				// This thread will handle the client
				// separately
				new Thread(clientSock).start();
				
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (server != null) {
				try {
					server.close();
					System.out.println("Server socket closed");
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ClientHandler class
	public static class ClientHandler implements Runnable {
		private final Socket clientSocket;
		
		private EmployeesData employee = new EmployeesData();
		private CustomersData customer = new CustomersData();
		private AccountsData account = new AccountsData();

		// Constructor
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
		}
		
		public void run()
		{

			OutputStream outputStream = null;
			InputStream inputStream = null;			
			
			try {
				
				// Output stream socket.
		        outputStream = clientSocket.getOutputStream();

		        // Create object output stream from the output stream to send an object through it
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				
		        // get the input stream from the connected socket
		        inputStream = clientSocket.getInputStream();

		        // create a ObjectInputStream so we can read data from it.
		        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);        

		        Message message = null;
		        

		        while((message = (Message) objectInputStream.readObject()) != null) {
		        	/* Pre-loading database with every message object received,
		        	 * to ensure most current data from database is viewed.
		        	 */
		        	String bankName = "Bank" + message.getBankNum();
		        	employee.loadData(bankName + "-Employees.txt");
			        customer.loadData(bankName + "-Customers.txt");
			        account.loadData(bankName + "-Accounts.txt");
			        
			        //File path tester
			        //System.out.println(account.toString());
			       	
			        //ATM type calls ATM handler method
			        if("atm".equalsIgnoreCase(message.getSender())){
			        	message = atmHandler(message); 
			        	objectOutputStream.writeObject(message);	
			        }
			        
			        //desktop type calls desktop handler method
		        	if("desktop".equalsIgnoreCase(message.getSender())){
		        			message = desktopHandler(message);
		        			objectOutputStream.writeObject(message);
				    }
			    
			}

		        if (objectOutputStream != null) {
		        	objectOutputStream.close();
		        	//System.out.println("Output stream closed");
				}
				if (objectInputStream != null) {
					objectInputStream.close();
					//System.out.println("Input stream closed");
					clientSocket.close();
					//System.out.println("Client socket closed");
				}
		        //System.out.print("Client socket closed");
		        //clientSocket.close();
			}
			catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		//private client handler methods
		
		private Message desktopHandler(Message dtMsg) {
			if("login".equalsIgnoreCase(dtMsg.getType())) {
				int numInd = 2;
				
				int empUnInd = 0;
				int empPwInd = 1;

				int counter = 0;
				String[] splitText = dtMsg.getText().split(",");
				String[] signIn = new String [numInd];
				
				for(String a : splitText) {
					signIn[counter] = a;
					counter++;
				}
	        	
				if(!employee.isEmployee(signIn[empUnInd],signIn[empPwInd])) {
					dtMsg.setStatus("failed");
				}
				else {
					dtMsg.setStatus("success");
				}
		        
		        return dtMsg;
		    }
			
			if("inquiry".equalsIgnoreCase(dtMsg.getType())) {
				int numInd = 3;
				
				int acctInd = 0;
				int cardInd = 1;
				int pinInd = 2;
				
				int counter = 0;
				String[] splitText = dtMsg.getText().split(",");
				String[] querry = new String [numInd];
				
				for(String a : splitText) {
					querry[counter] = a;
					counter++;
				}

				if(customer.isAccountBelongToCard(querry[acctInd],querry[cardInd],querry[pinInd])) {
					dtMsg.setStatus("success");
					dtMsg.setText(customer.getCustInfo(querry[acctInd]) + "," + getAccount(querry[acctInd]));
				}
				else {
					dtMsg.setStatus("failed");
					dtMsg.setText("Information provided does not match");
				}
				return dtMsg;
			}
			
			if("authorization".equalsIgnoreCase(dtMsg.getType())) {

				int numInd = 2;
				
				int empUnInd = 0;
				int empPwInd = 1;
				
				int counter = 0;
				String[] splitText = dtMsg.getText().split(",");
				String[] signIn = new String [numInd];
				
				for(String a : splitText) {
					signIn[counter] = a;
					counter++;
				}
	        	
				if(!employee.isAuthorized(signIn[empUnInd],signIn[empPwInd])) {
					dtMsg.setStatus("not authorized");
				}
				else {
					dtMsg.setStatus("authorized");
				}
		        
		        return dtMsg;
			}
			
			if("update".equalsIgnoreCase(dtMsg.getType())) {
				
				/*Index allocated in accordance with Customer.toString() + "," + Account.toString()
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
				
				int counter = 0;
				String[] splitText = dtMsg.getText().split(",");
				String[] recInfo = null;
				
				recInfo = new String [numInd];
					
				for(String a : splitText) {
					recInfo[counter] = a;
					counter++;
				}

				if(recInfo[acctStatInd].equals("in used")) {
					recInfo[acctStatInd] = "not used";
				}
						
				customer.addOrModifyCustomer(recInfo[svgAcctInd],recInfo[chkgAcctInd],recInfo[cardInd],
						recInfo[pinInd],recInfo[lNameInd],recInfo[fNameInd],recInfo[addressInd]);
				
				account.addOrModifyAccount(recInfo[acctNumInd],recInfo[acctTypeInd],recInfo[acctStatInd],
						recInfo[acctBalInd]);
				
				customer.save();
				account.save();
				
				dtMsg.setStatus("success");
				dtMsg.setText("Update completed");
				return dtMsg;

			}
			
			if("logout".equalsIgnoreCase(dtMsg.getType())){
				dtMsg.setStatus("success");
				return dtMsg;
			}
			else {
				return null;
			}
		}
		
		private Message atmHandler(Message atmMsg) {
			if("login".equalsIgnoreCase(atmMsg.getType())) {
				int numInd = 2;
				
				int cardInd = 0;
				int pinInd = 1;
				
				int counter = 0;
				String[] splitText = atmMsg.getText().split(",");
				String[] querry = null;
				
				if(splitText.length == 2) {
					querry = new String [numInd];
					
					for(String a : splitText) {
						querry[counter] = a;
						counter++;
					}
					
					if(!(customer.isCard(querry[cardInd],querry[pinInd]))) {
						String chkgAcct = customer.getChkgAcctNum(querry[cardInd]);
						atmMsg.setStatus("success");
						atmMsg.setText(getAccount(chkgAcct));
					}
				}	
			}
			
			if("logout".equalsIgnoreCase(atmMsg.getType())) {
				int numInd = 4;
				
				int acctInd = 0;
				int acctTypeInd = 1;
				int acctStatInd = 2;
				int acctBalInd = 3;
				
				int counter = 0;
				String[] splitText = atmMsg.getText().split(",");
				String[] recInfo = null;
				
				recInfo = new String [numInd];
					
				for(String a : splitText) {
					recInfo[counter] = a;
					counter++;
				}

				if(recInfo[acctStatInd].equals("in used")) {
					recInfo[acctStatInd] = "not used";
				}

					account.addOrModifyAccount(recInfo[acctInd],recInfo[acctTypeInd],
							recInfo[acctStatInd],recInfo[acctBalInd]);
					
					account.save();
					
					atmMsg.setStatus("success");
					atmMsg.setText("Have a good day");

			}
			return atmMsg;
		}
		
		//private helper methods
		
		private String getAccount(String acctNum) {
			if(!"in used".equalsIgnoreCase(account.getStatus(acctNum))) {
				account.setStatus(acctNum,"in used");
				account.save();
				return account.getAcctInfo(acctNum);
			}
			else {
				return "Account is in used";
			}
		}
		
	}
}
