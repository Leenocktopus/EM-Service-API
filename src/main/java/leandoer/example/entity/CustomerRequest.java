package leandoer.example.entity;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "service_request")

public class CustomerRequest {
    @Column(name = "request_date")
    java.util.Date time;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "descr")
    private String text;
    @Column(name = "cust_phone")
    private String phone;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus status;

    public CustomerRequest() {
    }

    @PrePersist
    public void setStatus() {
        if (this.status == null)
            this.status = RequestStatus.PENDING;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
