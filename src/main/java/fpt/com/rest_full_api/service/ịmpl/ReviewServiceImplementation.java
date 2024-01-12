package fpt.com.rest_full_api.service.ịmpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.model.Product;
import fpt.com.rest_full_api.model.Review;
import fpt.com.rest_full_api.model.UserEntity;
import fpt.com.rest_full_api.repository.ProductRepository;
import fpt.com.rest_full_api.repository.ReviewRepository;
import fpt.com.rest_full_api.request.ReviewRequest;
import fpt.com.rest_full_api.service.ProductService;
import fpt.com.rest_full_api.service.ReviewService;

 

@Service
public class ReviewServiceImplementation implements ReviewService {
	
	private ReviewRepository reviewRepository;
	private ProductService productService;
	private ProductRepository productRepository;
	
	public ReviewServiceImplementation(ReviewRepository reviewRepository,ProductService productService,ProductRepository productRepository) {
		this.reviewRepository=reviewRepository;
		this.productService=productService;
		this.productRepository=productRepository;
	}

	@Override
	public Review createReview(ReviewRequest req,UserEntity user) throws ProductException {

		Product product=productService.findProductById(req.getProductId());
		Review review=new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setStar(req.getStar());
		review.setCreatedAt(LocalDateTime.now());
		
//		product.getReviews().add(review);
		productRepository.save(product);
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
