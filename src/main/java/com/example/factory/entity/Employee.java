package com.example.factory.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String speciality;

    private String experience;

    private String name;

    private String surname;

    private Boolean isFree;

    @JsonIgnoreProperties(value = {"employees"}, allowSetters = true)
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Department department;
}
