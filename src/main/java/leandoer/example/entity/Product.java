package leandoer.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "prod_id")
    private String productId;
    @Column(name = "product_name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manuf_id")

    private Manufacturer manufacturer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")

    private Category category;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "amnt_in_stock")
    private int amountInStock;

    private String descr;
    @Column(name = "total_score")
    private float totalScore;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProductAttribute> productAttributes = new HashSet<>();
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProductComment> productComments = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<OrderProduct> orders = new HashSet<>();

    public Product() {
    }

    public Product(String productId, String name, BigDecimal price, Manufacturer manufacturer, Category category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.category = category;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float total_score) {
        this.totalScore = total_score;
    }

    @Override
    public String toString() {
        return this.productId + " " + this.category + " " + this.manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(int amountInStock) {
        this.amountInStock = amountInStock;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Set<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(Set<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public Set<ProductComment> getProductComments() {
        return productComments;
    }

    public void setProductComments(Set<ProductComment> productComments) {
        this.productComments = productComments;
    }

    public Set<OrderProduct> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderProduct> products) {
        this.orders = products;
    }
}
