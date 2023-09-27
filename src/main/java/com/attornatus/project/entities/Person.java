package com.attornatus.project.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_person")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "person_id",
            referencedColumnName = "id"
    )
    private List<Address> addresses = new ArrayList<>();

    public Person(String name, Date birthDay, List<Address> addresses) {
        this.name = name;
        this.birthDay = birthDay;
        this.addresses = addresses;
    }

    public Person(String name, Date birthDay) {
        this.name = name;
        this.birthDay = birthDay;
    }
}
