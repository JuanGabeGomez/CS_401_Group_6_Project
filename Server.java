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
		        	String bankName = "Bank" + message.getBankNum();
		        	employee.loadData(bankName + "-Employees.txt");
			        customer.loadData(bankName + "-Customers.txt");
			        account.loadData(bankName + "-Accounts.txt");
		        
		        	//Testing incoming message
		        	//System.out.println(message.getType());
		        	//System.out.println(message.getStatus());
		        	//System.out.println(message.getText());
		        	
		        	
		        	if("desktop".equalsIgnoreCase(message.getSender())){
			        	//String[] splitMsg = message.getText().split(",");
			        	//message.setStatus("success");
			        	//System.out.println(message.getStatus());
				        objectOutputStream.writeObject(desktopHandler(message));

				    }
		        /*if("desktop".equalsIgnoreCase(message.getSender()) &&
		        		"login".equalsIgnoreCase(message.getType())) {
		        	String[] splitMsg = message.getText().split(",");
		        	message.setStatus("success");
		        	//System.out.println(message.getStatus());
			        
			        objectOutputStream.writeObject(message);

			    }
		        else if("desktop".equalsIgnoreCase(message.getSender()) &&
		        		"logout".equalsIgnoreCase(message.getType())) {
		        	message.setStatus("success");
			        
			        objectOutputStream.writeObject(message);
			    }*/
		        
		        /*
		        else if("logout".equalsIgnoreCase(message.getType())) {
		        	message.setStatus("success");
		        	//System.out.println(message.getStatus());
			        
			        objectOutputStream.writeObject(message);
			    }
		        
		        else if("message".equalsIgnoreCase(message.getType())){
		        	System.out.println("From client: " + message.getText());
			        String temp = message.getText();
			        message.setText(temp.toUpperCase());
			        //System.out.println(message.getText());
			        objectOutputStream.writeObject(message);
			    }
			    */
			    
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
		
		public Message desktopHandler(Message dtMsg) {
			if("login".equalsIgnoreCase(dtMsg.getType())) {
				int counter = 0;
				String[] splitText = dtMsg.getText().split(",");
				String[] signIn = new String [2];
				
				for(String a : splitText) {
					signIn[counter] = a;
					counter++;
				}
	        	
				System.out.println("Username: " + signIn[0]);
				System.out.println("Password: " + signIn[1]);
				if(!employee.isEmployee(signIn[0],signIn[1])) {
					dtMsg.setStatus("failed");
				}
				else {
					dtMsg.setStatus("success");
				}
		        
		        return dtMsg;
		    }
			if("inquiry".equalsIgnoreCase(dtMsg.getType())) {
				//From desktop:
				//("1","desktop","inquiry","","account#,card#,pin#")
				int counter = 0;
				String[] splitText = dtMsg.getText().split(",");
				String[] signIn = new String [3];
				
				for(String a : splitText) {
					signIn[counter] = a;
					counter++;
				}

			}
			if("authorization".equalsIgnoreCase(dtMsg.getType())) {
				int counter = 0;
				String[] splitText = dtMsg.getText().split(",");
				String[] signIn = new String [2];
				
				for(String a : splitText) {
					signIn[counter] = a;
					counter++;
				}
	        	
				if(!employee.isAuthorized(signIn[0],signIn[1])) {
					dtMsg.setStatus("not authorized");
				}
				else {
					dtMsg.setStatus("authorized");
				}
		        
		        return dtMsg;
			}
			if("update".equalsIgnoreCase(dtMsg.getType())) {
				
			}
			if("logout".equalsIgnoreCase(dtMsg.getType())){
				dtMsg.setStatus("success");
				return dtMsg;
			}
			else {
				return null;
			}
		}
		
	}
}
