package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double price;

    @ManyToOne
    @JsonIgnore
    @JsonIgnoreProperties("products")
    private Seller seller;
}
