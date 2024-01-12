package fpt.com.rest_full_api.request;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;

 
import fpt.com.rest_full_api.model.Size;
import lombok.Data;

@Data
public class CreateProductRequest {
	
    private String title;

    private String description;

    private int price;

    private int discountedPrice;
   
    private int discountPersent;

    private int quantity;

    private String brand;

    private String color;

    private Set<Size> size=new HashSet<>();

    private List<MultipartFile> images=new ArrayList<>();

    private String topLavelCategory;

    private String secondLavelCategory;

    private String thirdLavelCategory;

	 
}
