package com.intern.project6.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employee", schema = "employee")
public class Employee {
    @Id
    private String id;
    private String name;
    private Integer salary;
}
