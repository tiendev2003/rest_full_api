package fpt.com.rest_full_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.exception.UserException;
import fpt.com.rest_full_api.model.Cart;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.request.AddItemRequest;
import fpt.com.rest_full_api.response.ApiResponse;
import fpt.com.rest_full_api.service.CartService;
import fpt.com.rest_full_api.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Cart API")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        UserEntity user = userService.findUserProfileByJwt(jwt);

        Cart cart = cartService.findUserCart(user.getId());

        System.out.println("cart - " + cart.getUser().getEmail());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        UserEntity user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(), req);

        ApiResponse res = new ApiResponse("Item Add To Cart", true);

        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);

    }

    @PostMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(@RequestHeader("Authorization") String jwt) throws UserException {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        cartService.clearCart(user.getId());
        return new ResponseEntity<>(new ApiResponse("Cart cleared successfully", true), HttpStatus.OK);
    }
}
