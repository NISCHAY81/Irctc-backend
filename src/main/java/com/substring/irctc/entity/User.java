package com.substring.irctc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private LocalDateTime createdAt;

//    private UserRole userRole = UserRole.ROLE_ADMIN;

    @OneToMany(mappedBy = "user")
   private List<Booking> bookings;

  @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles = new ArrayList<>();

 }
