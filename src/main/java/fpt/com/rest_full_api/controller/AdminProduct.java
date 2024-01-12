package fpt.com.rest_full_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.com.rest_full_api.exception.ProductException;
import fpt.com.rest_full_api.model.Product;
import fpt.com.rest_full_api.request.CreateProductRequest;
import fpt.com.rest_full_api.response.ApiResponse;
import fpt.com.rest_full_api.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin/products")
@Tag(name = "Admin Product", description = "Admin Product API")
public class AdminProduct {
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProductHandler(@ModelAttribute CreateProductRequest req) throws ProductException {

        Product createdProduct = productService.createProduct(req);

        return new ResponseEntity<Product>(createdProduct, HttpStatus.ACCEPTED);

    }
@PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable Long productId) throws ProductException {

        System.out.println("delete product controller .... ");
        String msg = productService.deleteProduct(productId);
        System.out.println("delete product controller .... msg " + msg);
        ApiResponse res = new ApiResponse(msg, true);

        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);

    }
    // @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct() {

        List<Product> products = productService.getAllProducts();

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProductHandler(@RequestBody Product req, @PathVariable Long productId)
            throws ProductException {

        Product updatedProduct = productService.updateProduct(productId, req);

        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] reqs)
            throws ProductException {

        for (CreateProductRequest product : reqs) {
            productService.createProduct(product);
        }

        ApiResponse res = new ApiResponse("products created successfully", true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }
}
