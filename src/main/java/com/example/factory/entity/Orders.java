package com.example.factory.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;


    @JsonIgnoreProperties(value = {"orders"}, allowSetters = true)
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    User client;

    private int price;

    private String name;

    private Boolean onProduction;

    public Orders(User client, Boolean onProduction) {
        this.client = client;
        this.onProduction = onProduction;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Product> products;
}
