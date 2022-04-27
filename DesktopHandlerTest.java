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
	public void stage1_testAcceptEmpLogin() {
		String username = "Cat";	// Cat/Dog/Duck
		String password = "1234";	// 1234/2345/3456
		String sender = "desktop";
		String type = "login";
		String status = "";
		
		String empLoginReq = username + "," + password;
		
		dtMsgTest = new Message(bankNum ,sender,type,status,empLoginReq);
		dtTest = new ClientHandlerMethod(dtMsgTest);
		String loginSuccess = dtTest.desktopHandler(dtMsgTest).getStatus();
		System.out.println("Employee login: " + loginSuccess);
		assertTrue("success".equalsIgnoreCase(loginSuccess));
	}
	
	@Test
	public void stage2_testAcceptManager() {
		String username = "Duck";
		String password = "3456";
		String sender = "desktop";
		String type = "authorization";
		String status = "";
		
		String empLoginReq = username + "," + password;
		
		dtMsgTest = new Message(bankNum ,sender,type,status,empLoginReq);
		dtTest = new ClientHandlerMethod(dtMsgTest);
		String authSuccess = dtTest.desktopHandler(dtMsgTest).getStatus();
		System.out.println("Employee login: " + authSuccess);
		assertTrue("authorized".equalsIgnoreCase(authSuccess));
	}
	
	@Test
	public void stage3_testLookUpAccount() {
		
	}
}
