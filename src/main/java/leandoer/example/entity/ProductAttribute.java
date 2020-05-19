package leandoer.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "product_attribute")
public class ProductAttribute {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Id doesn't give any information for api user since it used only for hibernate
    @JsonIgnore
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id")
    @JsonBackReference
    Product product;

    @Column(name = "attribute")
    String name;

    @Column(name = "value")
    String value;

    public ProductAttribute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productId) {
        this.product = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String attribute) {
        this.name = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name + " " + this.value;
    }
}
