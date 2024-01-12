package fpt.com.rest_full_api.service.ịmpl	;
import org.springframework.stereotype.Service;

import fpt.com.rest_full_api.model.OrderItem;
import fpt.com.rest_full_api.repository.OrderItemRepository;
import fpt.com.rest_full_api.service.OrderItemService;
 
@Service
public class OrderItemServiceImplementation implements OrderItemService {

	private OrderItemRepository orderItemRepository;
	public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}

}
