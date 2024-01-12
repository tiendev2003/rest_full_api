package fpt.com.rest_full_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.exception.UserException;
import fpt.com.rest_full_api.model.Review;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.request.ReviewRequest;
import fpt.com.rest_full_api.service.ReviewService;
import fpt.com.rest_full_api.service.UserService;

 
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	private ReviewService reviewService;
	private UserService userService;
	
	public ReviewController(ReviewService reviewService,UserService userService) {
		this.reviewService = reviewService;
		this.userService = userService;
	}
	@PostMapping("/create")
	public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		UserEntity user = userService.findUserProfileByJwt(jwt);
		System.out.println("product id " + req.getProductId() + " - "+ req.getReview());
		Review review = reviewService.createReview(req, user);
		System.out.println("product review " + req.getReview());
		return new ResponseEntity<Review>(review,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductsReviewHandler(@PathVariable Long productId){
		List<Review>reviews = reviewService.getAllReview(productId);
		return new ResponseEntity<List<Review>>(reviews,HttpStatus.OK);
	}

}
