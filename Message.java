/*
 * Majority of the codes are from Threaded Sockets and Object Over Socket code
 * provided in class by the professor.
 */
import java.io.Serializable;

//must implement Serializable in order to be sent
public class Message implements Serializable {
	protected String bankNum;
    protected String sender;
	protected String type;
    protected String status;
    protected String text;

    //Constructor
    public Message(){
    	this.bankNum = "Undefined";
    	this.sender = "Undefined";
        this.type = "Undefined";
        this.status = "Undefined";
        this.text = "Undefined";
    }

    //Overload constructor
    public Message(String bankNum, String sender, String type, String status, String text){
    	setBankNum(bankNum);
    	setSender(sender);
    	setType(type);
    	setStatus(status);
    	setText(text);
    }
    
    private void setBankNum(String newBankNum) {
    	this.bankNum = newBankNum;
    }
    
    private void setSender(String sender) {
    	this.sender = sender;
    }

    private void setType(String type){
    	this.type = type;
    }

    public void setStatus(String status){
    	this.status = status;
    }

    public void setText(String text){
    	this.text = text;
    }
    
    public String getBankNum() {
    	return bankNum;
    }

    public String getSender(){
    	return sender;
    }
    
    public String getType(){
    	return type;
    }

    public String getStatus(){
    	return status;
    }

    public String getText(){
    	return text;
    }

}
