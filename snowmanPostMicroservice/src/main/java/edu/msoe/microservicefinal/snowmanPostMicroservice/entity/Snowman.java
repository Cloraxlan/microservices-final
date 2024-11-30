package edu.msoe.microservicefinal.snowmanPostMicroservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "snowmen")
@Entity
@Getter
@Setter
public class Snowman {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String imageURI;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private int seenCount = 0;

    @Column(nullable = false)
    private int missingCount = 0;
}
