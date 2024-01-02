package fpt.com.rest_full_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductRelated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}