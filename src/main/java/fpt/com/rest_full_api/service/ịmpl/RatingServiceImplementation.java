package fpt.com.rest_full_api.service.ịmpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.model.Product;
import fpt.com.rest_full_api.model.Rating;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.repository.RatingRepository;
import fpt.com.rest_full_api.request.RatingRequest;
import fpt.com.rest_full_api.service.ProductService;
import fpt.com.rest_full_api.service.RatingServices;

@Service
public class RatingServiceImplementation implements RatingServices {

	private RatingRepository ratingRepository;
	private ProductService productService;

	public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
		this.ratingRepository = ratingRepository;
		this.productService = productService;
	}

	@Override
	public Rating createRating(RatingRequest req, UserEntity user) throws ProductException {

		Product product = productService.findProductById(req.getProductId());

		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());

		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {

		return ratingRepository.getAllProductsRating(productId);
	}

}
