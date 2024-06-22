package com.example.patterns.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<ContactDetails> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(Set<ContactDetails> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public void addContactDetail(ContactDetails contactDetail) {
        contactDetails.add(contactDetail);
        contactDetail.setUser(this);
    }

    public void removeContactDetail(ContactDetails contactDetail) {
        contactDetails.remove(contactDetail);
        contactDetail.setUser(null);
    }
}
