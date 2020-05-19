package leandoer.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "manufacturer")

@NoArgsConstructor
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manuf_id")
    int id;

    @Column(name = "name")
    String name;
    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    /*
    This set is a part of hibernate one-to-many relationship, but it will be ignored
    to make API cleaner
    */
    @JsonIgnore
    private Set<Product> productSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }
}
