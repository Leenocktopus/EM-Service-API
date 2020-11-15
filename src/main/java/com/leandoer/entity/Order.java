package com.leandoer.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "ecommerce", name = "orders")
@Setter
@Getter
@ToString
public class Order extends BaseEntity {

	@Column(name = "cust_name")
	private String customerName;

	@Column(name = "cust_phone")
	private String customerPhone;

	@Column(name = "cust_email")
	private String customerEmail;

	@Column(name = "order_date")
    private Timestamp date;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderProduct> products = new HashSet<>();


    public void addProduct(Product product, int quantity) {
        OrderProduct op = new OrderProduct(product, this, quantity);
        products.add(op);
        product.getOrders().add(op);
    }

    public void removeProduct(Product product) {
        for (Iterator<OrderProduct> iterator = products.iterator(); iterator.hasNext(); ) {
            OrderProduct op = iterator.next();
            if (op.getProduct().equals(product) && op.getOrder().equals(this)) {
                iterator.remove();
                op.getProduct().getOrders().remove(op);
                op.setOrder(null);
                op.setProduct(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order obj = (Order) o;
        return Objects.equals(getId(), obj.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
