package com.attornatus.project.entities;

import java.io.Serializable;

public class Adress implements Serializable {
//Classe de Endereço
    private String publicPlace; //logradouro
    private String zipCode; //CEP
    private Integer number; //número
    private String city; //cidade

    private Person person; //classe pessoa

    public Adress(String publicPlace, String zipCode, Integer number, String city, Person person) {
        this.publicPlace = publicPlace;
        this.zipCode = zipCode;
        this.number = number;
        this.city = city;
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
