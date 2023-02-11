package com.attornatus.project.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

//Classe de Endereço
@Entity
@Table(name = "tb_address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String publicPlace; //logradouro

    @Column(nullable = false)
    private Integer number; //número

    @Column(length = 9, nullable = false)
    @JsonFormat(pattern = "nnnnn-nnn")
    private String zipCode; //CEP

    @Column(length = 20, nullable = false)
    private String city; //cidade

    private Boolean isMain = false; //atributo que definirá se o endereço é o principal ou não

    @ManyToOne
    @JoinColumn(
            name = "person_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Person person; //classe pessoa

    public Address() {
    }

    public Address(Long id, String publicPlace, Integer number, String zipCode, String city, Person person, Boolean isMain) {
        this.id = id;
        this.publicPlace = publicPlace;
        this.number = number;
        this.zipCode = zipCode;
        this.city = city;
        this.person = person;
        this.isMain = isMain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
