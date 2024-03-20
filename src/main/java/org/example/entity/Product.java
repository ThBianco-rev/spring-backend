package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Product {

    // TODO: Set up spring annotations
    private long id;
    private String name;
    private double price;
    private int seller;

}
