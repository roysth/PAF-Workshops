package vttp2022.paf.assessment.eshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.respositories.OrderStatusRepository;

import static vttp2022.paf.assessment.eshop.Utilities.*;

@Service
public class WarehouseService {

	@Autowired
	private OrderStatusRepository orderStatusRepository;

	public static final String serverURL = "http://paf.chuklee.com/dispatch";
	public static final String createdBy = "roy";

	// You cannot change the method's signature
	// You may add one or more checked exceptions
	public OrderStatus dispatch(Order order) {

		// TODO: Task 4

		OrderStatus orderStatus;
		
		//Create the json object to be sent to warehouse
		//Requirements are listed in the paper
		/*
		Since the toJson method in utilities is static, can just write as what is written below with
		import static vttp2022.paf.assessment.eshop.Utilities.*;

		If it is not static, then have to:
		@Autowired
		private Utilities utilities;
		JsonObject object = utilities.toJson(order, createdBy);
		*/
		JsonObject object = toJson(order, createdBy);

		//Build the URL to connect to the server (warehouse)
		String url = UriComponentsBuilder.fromUriString(serverURL)
			.pathSegment(order.getOrderId())
			.toUriString();
		
		System.out.printf("URL = %s\n", url);

		//Using the url and json object to communicate w warehouse server [POST method]
		//use post since its POST
		//object.toString() as xfers are alwyas in string
		RequestEntity<String> request = RequestEntity
			.post(url)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(object.toString());

		try {
			RestTemplate restTemplate = new RestTemplate();
			//Response from the warehouse server, which will always return as a string
			ResponseEntity<String> response = restTemplate.exchange(request, String.class);
			String payload = response.getBody();
			//Need to convert into json to create a orderStatus object (always work w json)
			//Create a orderStatus for SUCCCESSFUL dispatch
			orderStatus = createOrderStatus(toJson(payload));
			
		} catch (RestClientException ex) {
			//Create a orderStatus for UNSUCCCESSFUL dispatch
			orderStatus = new OrderStatus();
			orderStatus.setOrderId(order.getOrderId());
		}

		//Update the sql
		orderStatusRepository.insertOrderStatus(orderStatus);

		return orderStatus;

	}
}
