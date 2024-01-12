package fpt.com.rest_full_api.service.ịmpl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.model.Category;
import fpt.com.rest_full_api.model.Image;
import fpt.com.rest_full_api.model.Product;
import fpt.com.rest_full_api.repository.CategoryRepository;
import fpt.com.rest_full_api.repository.ProductRepository;
import fpt.com.rest_full_api.request.CreateProductRequest;
import fpt.com.rest_full_api.service.ProductService;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private Cloudinary cloudinary;

	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

	@Override
	public Product createProduct(CreateProductRequest req) {

		Category topLevel = categoryRepository.findByName(req.getTopLavelCategory());

		if (topLevel == null) {

			Category topLavelCategory = new Category();
			topLavelCategory.setName(req.getTopLavelCategory());
			topLavelCategory.setLevel(1);

			topLevel = categoryRepository.save(topLavelCategory);
		}

		Category secondLevel = categoryRepository.findByNameAndParant(req.getSecondLavelCategory(), topLevel.getName());
		if (secondLevel == null) {

			Category secondLavelCategory = new Category();
			secondLavelCategory.setName(req.getSecondLavelCategory());
			secondLavelCategory.setParentCategory(topLevel);
			secondLavelCategory.setLevel(2);

			secondLevel = categoryRepository.save(secondLavelCategory);
		}

		Category thirdLevel = categoryRepository.findByNameAndParant(req.getThirdLavelCategory(),
				secondLevel.getName());
		if (thirdLevel == null) {

			Category thirdLavelCategory = new Category();
			thirdLavelCategory.setName(req.getThirdLavelCategory());
			thirdLavelCategory.setParentCategory(secondLevel);
			thirdLavelCategory.setLevel(3);

			thirdLevel = categoryRepository.save(thirdLavelCategory);
		}

		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPersent(req.getDiscountPersent());

		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setStatus(0);
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());

		Path staticPath = Paths.get("src/main/resources/static");
		Path imagePath = Paths.get("uploads");
		List<Image> images = req.getImages().stream().map(m -> {
			Image img = new Image();
			Path file = CURRENT_FOLDER.resolve(staticPath)
					.resolve(imagePath).resolve(m.getOriginalFilename());
			try (OutputStream os = Files.newOutputStream(file)) {
				Map<String, Object> options = Map.of("public_id", "uploads/" + m.getOriginalFilename(), "overwrite",
						true, "resource_type", "auto");
				// Map<String, Object> map = this.cloudinary.uploader().upload(m.getBytes(), Map.of());

				img.setName("uploads/" + m.getOriginalFilename());
				os.write(m.getBytes());
				os.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
			img.setProduct(product);
			return img;
		}).collect(Collectors.toList());
		product.setImages(images);

		Product savedProduct = productRepository.save(product);

		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {

		Product product = findProductById(productId);

		System.out.println("delete product " + product.getId() + " - " + productId);
		// product.getSizes().clear();
		// productRepository.save(product);
		// product.getCategory().
		// productRepository.delete(product);
		product.setStatus(1);
		productRepository.save(product);

		return "Stopped Selling Success";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {

		Product product = findProductById(productId);

		if (req.getImages() != null) {
			product.setImages(req.getImages());
		}

		if (req.getTitle() != null) {
			product.setTitle(req.getTitle());
		}

		if (req.getPrice() != 0) {
			product.setPrice(req.getPrice());
		}

		if (req.getDiscountedPrice() != 0) {
			product.setDiscountedPrice(req.getDiscountedPrice());
		}

		if (req.getDiscountPersent() != 0) {
			product.setDiscountPersent(req.getDiscountPersent());
		}

		if (req.getBrand() != null) {
			product.setBrand(req.getBrand());
		}

		if (req.getColor() != null) {
			product.setColor(req.getColor());
		}

		if (req.getQuantity() != 0) {
			product.setQuantity(req.getQuantity());
		}

		if (req.getDescription() != null) {
			product.setDescription(req.getDescription());
		}

		if (req.getCategory() != null) {
			product.setCategory(req.getCategory());
		}

		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product findProductById(Long id) throws ProductException {

		Optional<Product> opt = productRepository.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		}

		throw new ProductException("product not found with id " + id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {

		System.out.println("category --> " + category);

		List<Product> products = productRepository.findByCategory(category);

		return products;
	}

	@Override
	public List<Product> findProductByBrand(String brand) {

		System.out.println("brand --> " + brand);

		List<Product> products = productRepository.findByBrand(brand);

		return products;
	}

	@Override
	public List<Product> searchProduct(String query) {
		List<Product> products = productRepository.searchProduct(query);
		return products;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors,
			List<String> sizes, Integer minPrice, Integer maxPrice,
			Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

		if (!colors.isEmpty()) {
			products = products.stream()
					.filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}

		if (stock != null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
		return filteredProducts; // If color list is empty, do nothing and return all products

	}

}
