package com.example;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;
}
