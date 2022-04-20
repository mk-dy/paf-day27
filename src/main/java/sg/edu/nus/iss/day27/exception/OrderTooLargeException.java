package sg.edu.nus.iss.day27.exception;

public class OrderTooLargeException extends Exception {

    public OrderTooLargeException() {
        super();
    }
    
    public OrderTooLargeException(String msg){
        super(msg);
    }
    
    
}
