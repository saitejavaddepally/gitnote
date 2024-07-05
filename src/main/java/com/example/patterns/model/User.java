package com.example.patterns.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roles; // Comma-separated roles

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ContactDetails> contactDetails = new HashSet<>();

    // Getters and setters

    public void addContactDetail(ContactDetails contactDetail) {
        contactDetails.add(contactDetail);
        contactDetail.setUser(this);
    }

    public void removeContactDetail(ContactDetails contactDetail) {
        contactDetails.remove(contactDetail);
        contactDetail.setUser(null);
    }
}
