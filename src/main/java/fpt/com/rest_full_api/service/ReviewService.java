package fpt.com.rest_full_api.service;

import java.util.List;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.model.Review;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.request.ReviewRequest;
 
public interface ReviewService {

	public Review createReview(ReviewRequest req, UserEntity user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
	
}
