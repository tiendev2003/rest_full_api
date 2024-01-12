package fpt.com.rest_full_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.com.rest_full_api.model.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
