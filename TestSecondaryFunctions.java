import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestSecondaryFunctions {
	
	@Test
	public void testStringConversion() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 1234);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String number = "1";
		ATM atm1 = new ATM(socket, number);
		atm1.enableDebug();
		String stringToParse = 1234 + " , "
				+ "Checking , "
				+ 1000 + " @ ";
		Account user = new Account("1234", "Checking", 1000);
		assertTrue(user.toString().equals(atm1.debugStringToAccount(stringToParse).toString()));
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testPrintReceipt(){
		Socket socket = null;
		try {
			socket = new Socket("localhost", 1234);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String number = "1";
		ATM atm1 = new ATM(socket, number);
		atm1.enableDebug();
		atm1.login("1","1");
		Account user = new Account("1234", "Checking", 1000);
		String testReceipt = "Account: " + user.getNumber() + "\n"
				+ "Account type: " + user.getType() + "\n"
				+ "Balance: $" + user.getBalance();
		assertTrue(atm1.getReceipt().equals(testReceipt));
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetters() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 1234);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String number = "1";
		ATM atm1 = new ATM(socket, number);
		atm1.enableDebug();
		atm1.login("1","1");
		assertTrue(atm1.getSocket() == socket);
		assertTrue(atm1.getBankNumber().equals(number));
		assertTrue(atm1.getLoggedIn());
	}
	
}
