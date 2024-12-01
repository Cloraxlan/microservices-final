package edu.msoe.microservicefinal.snowmanPostMicroservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User {
    @Id
    @Column(nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(name = "user_notifications", joinColumns = @JoinColumn(name = "email"))
    @Column(name = "notify_cities")
    private List<String> notifyCities;
}
