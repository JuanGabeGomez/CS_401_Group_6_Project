import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runners.MethodSorters;
//import org.junit.jupiter.api.Test;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DesktopHandlerTest {
	
	private String bankNum = "1";
	private String bankName = "Bank" + bankNum;
	private String acctFile = bankName + "-Accounts.txt";
	
	private Message dtMsgTest = null;
	private ClientHandlerMethod dtTest = null;
	
	private AccountsData aAccount = new AccountsData();
	
	private AccountsData bAccount = new AccountsData();
	
	public DesktopHandlerTest() {

	}
	
	@Test
	public void stage1_testLoginType() {
		System.out.println("***Bank1-Internal Test***\n");
		String username = "Cat";	// Cat/Dog/Duck *fail if username used is not as listed
		String password = "1234";	// 1234/2345/3456 *fail if password used is not as listed
		String sender = "desktop";
		String type = "login";
		String status = "";
		String text = username + "," + password;
		
		dtMsgTest = new Message(bankNum ,sender,type,status,text);
		dtTest = new ClientHandlerMethod(dtMsgTest);
		
		Message svrMsg = dtTest.desktopHandler(dtMsgTest);
		String msgStatus = svrMsg.getStatus();						// Client looks for return status
		
		System.out.println("Employee login: " + msgStatus);
		assertTrue("success".equalsIgnoreCase(msgStatus));
	}
	
	@Test
	public void stage2_testAuthorizationType() {
		String username = "Duck";									// Failed if username&password does not match or low level authorization
		String password = "3456";
		String sender = "desktop";
		String type = "authorization";
		String status = "";	
		String text = username + "," + password;
		
		dtMsgTest = new Message(bankNum ,sender,type,status,text);
		dtTest = new ClientHandlerMethod(dtMsgTest);
		
		Message svrMsg = dtTest.desktopHandler(dtMsgTest);
		String msgStatus = svrMsg.getStatus();						// Client looks for return status
		
		System.out.println("\nAuthorization login: " + msgStatus);
		assertTrue("authorized".equalsIgnoreCase(msgStatus));
	}
	
	@Test
	public void stage3_testInquiryType() {

		String sender = "desktop";
		String type = "inquiry";
		String status = "";
		String text = "4567,888888,0202";							// Failed if set of numbers are not found
		
		dtMsgTest = new Message(bankNum ,sender,type,status,text);
		dtTest = new ClientHandlerMethod(dtMsgTest);
		
		Message svrMsg = dtTest.desktopHandler(dtMsgTest);
		String msgText = svrMsg.getText();
		System.out.println("\nCustomerInfo and AccountInfo: " + msgText);
		String[] splitText = msgText.split(",");
		int numOfObj = splitText.length;
		System.out.println("Number of object in text: " + numOfObj);
		
		String msgStatus = svrMsg.getStatus();
		System.out.println("Inquiry 'status' from server: " + msgStatus);
		assertTrue("success".equalsIgnoreCase(msgStatus) && (numOfObj == 11));
	}
	
	@Test
	public void stage4_testUpdateType() {
		String sender = "desktop";
		String type = "update";
		String status = "";
		
		String changeInBal = "5.00";								// Test fail if number other than "5.00"
		String customer = "3456,4567,888888,0202,Doe,J,96 Sun";		// If different customer test will fail
		String account = "4567,checking,in use," + changeInBal;		// If different account test will fail
		String text = customer + "," + account;
		
		dtMsgTest = new Message(bankNum ,sender,type,status,text);
		dtTest = new ClientHandlerMethod(dtMsgTest);
		
		Message svrMsg = dtTest.desktopHandler(dtMsgTest);
		String msgStatus = svrMsg.getStatus();
		aAccount.loadData(acctFile);
		String currBal = aAccount.getBalance("4567");				// If number does not match, test will fail
		
		System.out.println("\nUpdate: " + msgStatus);
		
		// Set account back to original
		aAccount.loadData(acctFile);
		aAccount.setBalance("4567", "2.00");
		aAccount.save();
		assertTrue("success".equalsIgnoreCase(msgStatus) && (currBal.equals("5.00")));
	}
	
	@Test
	public void stage5_testLogout() {
		String sender = "desktop";
		String type = "logout";
		String status = "";
		String text = "";
		
		dtMsgTest = new Message(bankNum ,sender,type,status,text);
		dtTest = new ClientHandlerMethod(dtMsgTest);
		
		Message svrMsg = dtTest.desktopHandler(dtMsgTest);
		String msgStatus = svrMsg.getStatus();
		
		System.out.println("\nLogout status: " + msgStatus);
		assertTrue("success".equalsIgnoreCase(msgStatus));
	}
	
	@Test
	public void stage6_testNotType() {
		String sender = "desktop";
		String type = "login";
		String status = "i'm fine";
		String text = "how are you";
		
		dtMsgTest = new Message(bankNum ,sender,type,status,text);
		dtTest = new ClientHandlerMethod(dtMsgTest);
		
		Message svrMsg = dtTest.desktopHandler(dtMsgTest);
		String message = svrMsg.getBankNum() + "," + svrMsg.getSender() + "," + svrMsg.getType()
		 + "," + svrMsg.getStatus() + "," + svrMsg.getText();
		System.out.println("\nNo 'type' return from server: " + message);
		assertTrue(dtMsgTest == svrMsg);
	}
}
