package fpt.com.rest_full_api.service;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.model.Cart;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.request.AddItemRequest;

public interface CartService {

    public Cart createCart(UserEntity user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

    public void clearCart(Long userId);
}