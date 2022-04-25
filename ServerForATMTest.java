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
				System.out.println("New client connected "
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
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ClientHandler class
	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;

		// Constructor
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
		}

		public void run()
		{
			PrintWriter out = null;
			BufferedReader in = null;
			try {
				Account user = new Account("1234", "checking", "in used", "1000");
				
				String sendover = user.getAcctNum() + " , "
						+ user.getType() + " , "
						+ "in used , "
						+ user.getBalance() + " @ ";
				
				// get the input stream from the connected socket
		        InputStream inputStream = clientSocket.getInputStream();

		        // create a ObjectInputStream so we can read data from it.
		        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		        
		        // Output stream socket.
		        OutputStream outputStream = clientSocket.getOutputStream();
		        // Create object output stream from the output stream to send an object through it
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		        
		        //Login
				Message login = (Message) objectInputStream.readObject();
				login.setStatus("success");
				login.setText(sendover);
				objectOutputStream.writeObject(login);
				System.out.println("Starting customer: "
						+ user.toString());
				//logout
		        Message logout =  (Message) objectInputStream.readObject();
		        logout.setStatus("success");
		        logout.setText("Have a good day");
		        objectOutputStream.writeObject(logout);
			}
			catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
						clientSocket.close();
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

}