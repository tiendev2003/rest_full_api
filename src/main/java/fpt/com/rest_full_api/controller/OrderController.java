package fpt.com.rest_full_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.com.rest_full_api.exception.OrderException;
import fpt.com.rest_full_api.exception.UserException;
import fpt.com.rest_full_api.model.Address;
import fpt.com.rest_full_api.model.Order;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.repository.OrderRepository;
import fpt.com.rest_full_api.service.OrderService;
import fpt.com.rest_full_api.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private OrderService orderService;
	private UserService userService;

	@Autowired
	private OrderRepository orderRepository;

	public OrderController(OrderService orderService, UserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<Order> createOrderHandler(@RequestBody Address spippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserException {

		UserEntity user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.createOrder(user, spippingAddress);

		return new ResponseEntity<Order>(order, HttpStatus.OK);

	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization") String jwt)
			throws OrderException, UserException {

		UserEntity user = userService.findUserProfileByJwt(jwt);
		List<Order> orders = orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> findOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException, UserException {

		UserEntity user = userService.findUserProfileByJwt(jwt);
		Order orders = orderService.findOrderById(orderId);
		return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
	}

	@GetMapping("/placed")
	public ResponseEntity<List<Order>> getPlacedOrdersForUser(@RequestHeader("Authorization") String jwt)
			throws UserException {
		UserEntity user = userService.findUserProfileByJwt(jwt);
		List<Order> placedOrders = orderRepository.getPlacedOrders(user.getId());
		return new ResponseEntity<>(placedOrders, HttpStatus.OK);
	}

	@GetMapping("/confirmed")
	public ResponseEntity<List<Order>> getConfirmedOrdersForUser(@RequestHeader("Authorization") String jwt)
			throws UserException {
		UserEntity user = userService.findUserProfileByJwt(jwt);
		List<Order> confirmedOrders = orderRepository.getConfirmedOrders(user.getId());
		return new ResponseEntity<>(confirmedOrders, HttpStatus.OK);
	}

	@GetMapping("/shipped")
	public ResponseEntity<List<Order>> getShippedOrdersForUser(@RequestHeader("Authorization") String jwt)
			throws UserException {
		UserEntity user = userService.findUserProfileByJwt(jwt);
		List<Order> shippedOrders = orderRepository.getShippedOrders(user.getId());
		return new ResponseEntity<>(shippedOrders, HttpStatus.OK);
	}

	@GetMapping("/delivered")
	public ResponseEntity<List<Order>> getDeliveredOrdersForUser(@RequestHeader("Authorization") String jwt)
			throws UserException {
		UserEntity user = userService.findUserProfileByJwt(jwt);
		List<Order> deliveredOrders = orderRepository.getDeliveredOrders(user.getId());
		return new ResponseEntity<>(deliveredOrders, HttpStatus.OK);
	}

	@GetMapping("/cancelled")
	public ResponseEntity<List<Order>> getCancelledOrdersForUser(@RequestHeader("Authorization") String jwt)
			throws UserException {
		UserEntity user = userService.findUserProfileByJwt(jwt);
		List<Order> cancelledOrders = orderRepository.getCancelledOrders(user.getId());
		return new ResponseEntity<>(cancelledOrders, HttpStatus.OK);
	}

	@GetMapping("/success")
	public ResponseEntity<List<Order>> getSuccessOrdersForUser(@RequestHeader("Authorization") String jwt)
			throws UserException {
		UserEntity user = userService.findUserProfileByJwt(jwt);
		List<Order> successOrders = orderRepository.getSuccessOrders(user.getId());
		return new ResponseEntity<>(successOrders, HttpStatus.OK);
	}
}
