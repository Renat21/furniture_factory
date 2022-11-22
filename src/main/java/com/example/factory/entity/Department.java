package com.example.factory.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String address;

    private String telephoneNumber;

    private String specialization;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "department_id")}
            ,inverseJoinColumns = {@JoinColumn(name = "raws_id")}
    )
    private List<Raws> raws;

    @OneToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    private List<Employee> employees;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

}
