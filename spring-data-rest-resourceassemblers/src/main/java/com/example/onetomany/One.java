package com.example.onetomany;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class One {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String other;

    @OneToMany(cascade = ALL)
    private List<Many> manies = new ArrayList<>();

}
