package fpt.com.rest_full_api.service;
import java.util.List;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.model.Rating;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.request.RatingRequest;

 

public interface RatingServices {
	
	public Rating createRating(RatingRequest req,UserEntity user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}
