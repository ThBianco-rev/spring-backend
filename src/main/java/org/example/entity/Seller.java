package org.example.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name;
    @OneToMany
    @JoinColumn(name="seller_fk")
    public List<Seller> sellers;
}
