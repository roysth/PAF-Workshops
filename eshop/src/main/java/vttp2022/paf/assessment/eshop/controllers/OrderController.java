package vttp2022.paf.assessment.eshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.models.OrderStatusCount;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderStatusRepository;
import vttp2022.paf.assessment.eshop.services.OrderException;
import vttp2022.paf.assessment.eshop.services.OrderService;
import vttp2022.paf.assessment.eshop.services.WarehouseService;

import static vttp2022.paf.assessment.eshop.Utilities.*;

import java.util.Optional;

@RestController
public class OrderController {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private OrderService orderService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private OrderStatusRepository orderStatusRepository;


	//TODO: Task 3a
	//To check the path, run the app and insert the inputs. Inspect -> Network
	@PostMapping (path ="/api/order", consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity <String> postOrder (@RequestBody String payload) {

		// Request payload:
		// {name: "fred", 
		// lineItems: [
		//	{item: "cookie", quantity: 1},
		//	{item: "candies", quantity: 2},
		//	]
		// }

		//Task 3a:

		//Create the jsonObject based on the input
		JsonObject jsonObject = toJson(payload);
		String name = jsonObject.getString("name");

		Optional<Customer> optionalCustomer = customerRepo.findCustomerByName(name);

		//Customer customer = optionalCustomer.get();
		if (optionalCustomer.isEmpty()) {

			JsonObject error = errorMessage("Customer %s not found".formatted(name));

			return ResponseEntity 
			.status(HttpStatus.NOT_FOUND)
			.contentType(MediaType.APPLICATION_JSON)
			.body(error.toString());
		}

		//Task 3b, 3d:
		//Create the Order object given the jsonObject
		Order order = createOrder(jsonObject);
		//Create the Customer object 
		Customer customer = optionalCustomer.get();

		try {
			orderService.addNewOrder(order, customer);
		} catch (OrderException e) {
			//e.getMessage() returns a brief description of the exception/error that occurred as string
			JsonObject error = errorMessage(e.getMessage());

			return ResponseEntity 
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.contentType(MediaType.APPLICATION_JSON)
			.body(error.toString());
		}

		//Task 4 and 5:
		//Create orderStatus object
		OrderStatus orderStatus = warehouseService.dispatch(order);

		JsonObject object = toJson(orderStatus);

		return ResponseEntity 
		.status(HttpStatus.OK)
		.contentType(MediaType.APPLICATION_JSON)
		.body(object.toString());

	}

	//Task 6
	@GetMapping (path = "/api/order/{name}/name")
	public ResponseEntity<String> getOrderStatus (@PathVariable String name) {

		OrderStatusCount orderStatusCount = orderStatusRepository.orderStatusCount(name);

		JsonObject object = toJson(orderStatusCount);

		return ResponseEntity 
		.status(HttpStatus.OK)
		.contentType(MediaType.APPLICATION_JSON)
		.body(object.toString());
	}

}
