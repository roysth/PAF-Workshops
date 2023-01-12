package vttp.day24;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import vttp.day24.models.LineItem;
import vttp.day24.models.PurchaseOrder;
import vttp.day24.services.OrderException;
import vttp.day24.services.OrderService;

//Using this to test the Inserting of data into the schema
@SpringBootApplication
public class Day24Application implements CommandLineRunner {

	public static String[] DESC = {"apple", "orange", "pear", "grapes"};
	public static Integer[] QTY = {10, 5, 3, 5};

	@Autowired OrderService ordSvc;


	public static void main(String[] args) {
		SpringApplication.run(Day24Application.class, args);
	} 

	@Override
	//String ... means can have multiple arguments
	public void run (String... args) {

		PurchaseOrder po = new PurchaseOrder();
		po.setName("roy");
		po.setOrderDate(new Date());

		for (int i=0; i < DESC.length; i++){
			po.addLineItem(new LineItem(DESC[i], QTY[i]));
		}

		// Create the purchase order
		try {
			ordSvc.createNewOrder(po);
		} catch (OrderException ex) {
			System.out.println(">>>>> " + ex.getMessage());
		}

	}

}
