import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runners.MethodSorters;
//import org.junit.jupiter.api.Test;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ATMHandlerTest {
	
	private String bankNum = "1";
	private String bankName = "Bank" + bankNum;
	private String acctFile = bankName + "-Accounts.txt";
	
	private Message atmMsgTest = null;
	private ClientHandlerMethod atmTest = null;
	
	private AccountsData aAccount = new AccountsData();
	
	private AccountsData bAccount = new AccountsData();
	
	public ATMHandlerTest() {

	}
	
	@Test
	public void stage1_testAcceptCard() {
		aAccount.loadData(acctFile);
		bAccount.loadData(acctFile);
		
		System.out.println("***Before login***");
		for(int i = 0; i < aAccount.getNumAcct(); i++) {
			System.out.println("Account 'status' B = A[" + i + "]: " 
				+ bAccount.getStatusByIndex(i).equals(aAccount.getStatusByIndex(i)));

			assertTrue(bAccount.getStatusByIndex(i).equals(aAccount.getStatusByIndex(i)));
		}
		
		//atmMsgTest = new Message("1","ATM","login","","777777,0201");		// Failure due to incorrect pin
		atmMsgTest = new Message("1","ATM","login","","777777,0101");
		atmTest = new ClientHandlerMethod(atmMsgTest);
		atmMsgTest = atmTest.atmHandler(atmMsgTest);

		bAccount.loadData(acctFile);
		
		System.out.println("\n***After login***");
		for(int i = 0; i < aAccount.getNumAcct(); i++) {
			boolean temp = bAccount.getStatusByIndex(i).equals(aAccount.getStatusByIndex(i));
			System.out.println("Account 'status' B = A[" + i + "]: " + temp);	
			if(i == 1) {
				assertFalse(temp);
			}
			else {
				assertTrue(bAccount.getStatusByIndex(i).equals(aAccount.getStatusByIndex(i)));
			}
		}
		System.out.println();
	}
	
	@Test 
	public void stage2_testRejectCard() {
		
		//Message atmMsgTwoTest = new Message("1","ATM","login","","888888,0202");	// Failure due to pin being correct
		Message atmMsgTwoTest = new Message("1","ATM","login","","888888,0101");
		atmTest = new ClientHandlerMethod(atmMsgTwoTest);
		String returnStatTwo = atmTest.atmHandler(atmMsgTwoTest).getStatus();
		System.out.println("***Testing Card and Pin not matching***");
		System.out.println("Card accepted: " + returnStatTwo + "\n");
		assertTrue(!"success".equalsIgnoreCase(returnStatTwo));
		atmTest = null;
	}
	
	@Test
	public void stage3_testRejectRepeatedCard() {
		//Message atmTwoMsgTest = new Message("1","ATM","login","","888888",0202");	// Failure due to card/pin being a different set
		Message atmTwoMsgTest = new Message("1","ATM","login","","777777,0101");
		atmTest = new ClientHandlerMethod(atmTwoMsgTest);
		String returnMsg = atmTest.atmHandler(atmTwoMsgTest).getText();
		
		System.out.println("***Test login into account in use***");
		System.out.println("Test return 'text' from server: " + returnMsg);
		assertTrue(returnMsg.equals("Account is in use"));
		
		System.out.println();

	}

	@Test
	public void stage4_testChangeAndSave() {
		bAccount.loadData(acctFile);
		
		String acctNum = "2345";
		
		System.out.println("***Before logout***");
		for(int i = 0; i < bAccount.getNumAcct(); i++) {
			System.out.println(bAccount.getAcctByIndex(i));
		}
		
		double deposit = Double.parseDouble(bAccount.getBalance(acctNum));
		deposit = deposit + 20.00;
		// Account: 2345 Type: checking Org Balance: 301.09
		String logoutString = "2345,checking,in use," + deposit;  // String simulate the text string of message that will be received by ATM
		atmMsgTest = new Message("1","ATM","logout","",logoutString);
		atmTest = new ClientHandlerMethod(atmMsgTest);
		
		String logoutStat = atmTest.atmHandler(atmMsgTest).getStatus();
		bAccount.loadData(acctFile);
		System.out.println("***After logout***");
		for(int i = 0; i < bAccount.getNumAcct(); i++) {
			System.out.println(bAccount.getAcctByIndex(i));
		}
		assertTrue("success".equalsIgnoreCase(logoutStat));
		
	}
}
