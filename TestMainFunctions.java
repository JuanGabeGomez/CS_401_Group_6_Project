import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestMainFunctions {
	
	@Test
	public void testConstructor(){
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
		assertTrue(atm1.getSocket() == socket && atm1.getBankNumber().equals(number));
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeposit() {
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
		float initial = Float.parseFloat(atm1.getUser().getBalance());
		float deposit = 1000;
		atm1.deposit(deposit);
		assertTrue(initial + deposit == Float.parseFloat(atm1.getUser().getBalance()));
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithdrawal(){
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
		float initial = Float.parseFloat(atm1.getUser().getBalance());
		int withdraw = 500;
		atm1.withdrawal(withdraw);
		float now = initial - withdraw;
		assertTrue(now == Float.parseFloat(atm1.getUser().getBalance()));
		atm1.withdrawal(600);
		float current = Float.parseFloat(atm1.getUser().getBalance());
		assertEquals(now, current, 0.1);
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}