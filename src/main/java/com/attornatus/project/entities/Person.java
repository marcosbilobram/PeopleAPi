package com.attornatus.project.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Classe de Pessoa
@Entity
@Table(name = "tb_person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String name; //Nome

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay; //Data de nascimento

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "person_id",
            referencedColumnName = "id"
    )
    private List<Address> addresses = new ArrayList<>(); //Endereços

    public Person() {
    }

    public Person(Long id, String name, Date birthDay, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.addresses = addresses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}
