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
	public void stage1_Bank1testAcceptCard() {
		System.out.println("***Bank1-ATM Test***\n");
		aAccount.loadData(acctFile);
		int aCount = 0;
		int bCount = 0;
		
		for(int i = 0; i < aAccount.getNumAcct(); i++) {
			if("not used".equalsIgnoreCase(aAccount.getStatusByIndex(i).getStatus())) {
				aCount++;
			}
		}
		System.out.println("Before login - Number of accounts 'not used' status: " + aCount);
		
		atmMsgTest = new Message("1","ATM","login","","777777,0101");
		atmTest = new ClientHandlerMethod(atmMsgTest);
		atmMsgTest = atmTest.atmHandler(atmMsgTest);
		
		String msgStatus = atmMsgTest.getStatus();
		System.out.println("ATM(correct pin) - Card accepted: " + msgStatus);

		bAccount.loadData(acctFile);
		for(int i = 0; i < bAccount.getNumAcct(); i++) {
			if("not used".equalsIgnoreCase(bAccount.getStatusByIndex(i).getStatus())) {
				bCount++;
			}
		}
		System.out.println("After login - Number of accounts 'not used' status: " + bCount);
		assertTrue(aCount != bCount && "success".equalsIgnoreCase(msgStatus));
	}
	
	@Test 
	public void stage2_Bank1testRejectCard() {		
		//atmMsgTest = new Message("1","ATM","login","","888888,0202");	// Failure due to pin being correct
		atmMsgTest = new Message("1","ATM","login","","888888,0101");
		atmTest = new ClientHandlerMethod(atmMsgTest);
		atmMsgTest = atmTest.atmHandler(atmMsgTest);
		
		String msgStatus = atmMsgTest.getStatus();
		System.out.println("\nATM(incorrect pin) - Card accepted: " + msgStatus);
		assertTrue(!"success".equalsIgnoreCase(msgStatus));
	}
	
	@Test
	public void stage3_Bank1testRejectUseRepeatedCard() {
		//atmMsgTest = new Message("1","ATM","login","","888888",0202");	// Failure due to card/pin being a different set
		atmMsgTest = new Message("1","ATM","login","","777777,0101");
		atmTest = new ClientHandlerMethod(atmMsgTest);
		atmMsgTest = atmTest.atmHandler(atmMsgTest);
		
		String msgText = atmMsgTest.getText();
		System.out.println("\nUsing card already in use: " + msgText);
		assertTrue("Account is in use".equalsIgnoreCase(msgText));
	}

	@Test
	public void stage4_Bank1testChangeAndSave() {
		bAccount.loadData(acctFile);
		
		String acctNum = "2345";
		
		System.out.println("\nATM - Before logout account info: " + bAccount.getAcctInfo(acctNum));
		
		double deposit = Double.parseDouble(bAccount.getBalance(acctNum));
		deposit = deposit + 20.00;

		String text = acctNum + ",checking,in use," + deposit;  // String simulate the text string of message that will be received by ATM
		atmMsgTest = new Message("1","ATM","logout","",text);
		atmTest = new ClientHandlerMethod(atmMsgTest);
		atmMsgTest = atmTest.atmHandler(atmMsgTest);
		
		String msgStatus = atmMsgTest.getStatus();
		System.out.println("ATM - Logout status: " + msgStatus);
		
		bAccount.loadData(acctFile);
		System.out.println("ATM - After logout account info: " + bAccount.getAcctInfo(acctNum));
		
		assertTrue("success".equalsIgnoreCase(msgStatus));	
	}
	
	@Test
	public void stage5_Bank2testAcceptCard() {
		System.out.println("\n***Bank2-ATM Test***\n");
		String altBankNum = "2";
		String sender = "atm";
		String type = "login";
		String status = "";
		String text = "999999,0303";
		
		String altBankName = "Bank" + altBankNum;
		acctFile = altBankName + "-Accounts.txt";
		
		aAccount.loadData(acctFile);
		int aCount = 0;
		int bCount = 0;
		
		for(int i = 0; i < aAccount.getNumAcct(); i++) {
			if("not used".equalsIgnoreCase(aAccount.getStatusByIndex(i).getStatus())) {
				aCount++;
			}
		}
		System.out.println("Before login - Number of accounts 'not used' status: " + aCount);
		
		atmMsgTest = new Message(altBankNum,sender,type,status,text);
		atmTest = new ClientHandlerMethod(atmMsgTest);
		atmMsgTest = atmTest.atmHandler(atmMsgTest);
		
		String msgStatus = atmMsgTest.getStatus();
		System.out.println("ATM(correct pin) - Card accepted: " + msgStatus);

		bAccount.loadData(acctFile);
		for(int i = 0; i < bAccount.getNumAcct(); i++) {
			if("not used".equalsIgnoreCase(bAccount.getStatusByIndex(i).getStatus())) {
				bCount++;
			}
		}
		System.out.println("After login - Number of accounts 'not used' status: " + bCount);
		assertTrue(aCount != bCount && "success".equalsIgnoreCase(msgStatus));
	}

	@Test
	public void stage6_Bank2testChangeAndSave() {
		String altBankNum = "2";
		String sender = "atm";
		String type = "logout";
		String status = "";
		String text = "";

		String altBankName = "Bank" + altBankNum;
		acctFile = altBankName + "-Accounts.txt";

		bAccount.loadData(acctFile);
		
		String acctNum = "6789";
		
		System.out.println("\nATM - Before logout account info: " + bAccount.getAcctInfo(acctNum));
		
		double deposit = Double.parseDouble(bAccount.getBalance(acctNum));
		deposit = deposit + 20.00;

		text = acctNum + ",checking,in use," + deposit;  // String simulate the text string of message that will be received by ATM
		atmMsgTest = new Message(altBankNum,sender,type,status,text);
		atmTest = new ClientHandlerMethod(atmMsgTest);
		atmMsgTest = atmTest.atmHandler(atmMsgTest);
		
		String msgStatus = atmMsgTest.getStatus();
		System.out.println("ATM - Logout status: " + msgStatus);
		
		bAccount.loadData(acctFile);
		System.out.println("ATM - After logout account info: " + bAccount.getAcctInfo(acctNum));
		
		assertTrue("success".equalsIgnoreCase(msgStatus));	
	}
}
