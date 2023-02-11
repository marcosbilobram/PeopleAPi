package com.attornatus.project.DTO;

import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonDTO {

    private Long id;
    private String name; // Nome
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay; // Data de nascimento

    private List<Address> addresses = new ArrayList<>(); // Endereços

    public PersonDTO() {
    }

    public PersonDTO(Person person) {
        id = person.getId();
        name = person.getName();
        birthDay = person.getBirthDay();
        addresses = person.getAddresses();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
