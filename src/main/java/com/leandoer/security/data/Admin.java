package com.leandoer.security.data;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(schema = "users", name = "users")
@Entity
public class Admin {
    @Transient
    Role role = Role.ADMIN;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;


    public Admin() {
    }

}
