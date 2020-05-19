package leandoer.example.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import leandoer.example.util.OrderDeserializer;
import leandoer.example.util.OrderSerializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@JsonSerialize(using = OrderSerializer.class)
@JsonDeserialize(using = OrderDeserializer.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    int orderId;

    @Column(name = "cust_phone")
    String customerPhone;
    @Column(name = "cust_name")
    String customerName;
    @Column(name = "cust_email")
    String customerEmail;
    @Column(name = "order_date")
    java.util.Date date;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "order")
    private List<OrderProduct> products = new ArrayList<>();

    public Order() {

    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    @PrePersist
    public void setStatus() {
        if (this.getOrderStatus() == null) {
            this.orderStatus = OrderStatus.PENDING;
        }
    }

    public void addProduct(Product product, int quantity) {
        OrderProduct orderItems = new OrderProduct();
        orderItems.setProduct(product);
        orderItems.setOrder(this);
        orderItems.setQuantity(quantity);
        products.add(orderItems);
        product.getOrders().add(orderItems);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int order_id) {
        this.orderId = order_id;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
