package com.example;

import static lombok.AccessLevel.NONE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Child {

    @GeneratedValue
    @Id
    @Setter(NONE)
    private Long id;

    @NonNull
    private String name;
}
