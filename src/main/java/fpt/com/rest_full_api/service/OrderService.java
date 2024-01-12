package fpt.com.rest_full_api.service;

import java.util.List;

import fpt.com.rest_full_api.exception.OrderException;
import fpt.com.rest_full_api.model.Address;
import fpt.com.rest_full_api.model.Order;
import fpt.com.rest_full_api.model.UserEntity;
 

public interface OrderService {
	
	public Order createOrder(UserEntity user, Address shippingAdress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(Long userId);
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId)throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order cancledOrder(Long orderId) throws OrderException;
	
	public List<Order>getAllOrders();
	
	public Order deleteOrder(Long orderId) throws OrderException;

	public Order updateOrder (Long orderId) throws OrderException;
	public Order successOrder (Long orderId) throws OrderException;
	
}
