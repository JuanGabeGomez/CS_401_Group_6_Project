import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MessageTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void testmessage() {
		Message message1 = new Message();
		System.out.println("Default Constructor ");
		System.out.println(message1.getBankNum());
		System.out.println(message1.getSender());
		System.out.println(message1.getType());
		System.out.println(message1.getStatus());
		System.out.println(message1.getText());
		
		
		System.out.println("Overload Constructor");
		Message message2 = new Message("012221","Customer","None", "Success","H");
		System.out.println(message2.getBankNum());
		System.out.println(message2.getSender());
		System.out.println(message2.getType());
		System.out.println(message2.getStatus());
		System.out.println(message2.getText());
		
	}

}
