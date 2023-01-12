package vttp.day24workshop.service;

//For user defined exception written in the Controller eg max can enter 5 order details in the list
//Remeber to include extends Exception
public class OrderException extends Exception {

    public OrderException() {
        super();
    }
    public OrderException(String message) {
        super(message);
    }
    
}
