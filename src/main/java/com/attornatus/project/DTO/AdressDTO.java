package com.attornatus.project.DTO;

import com.attornatus.project.entities.Adress;
import com.attornatus.project.entities.Person;

public class AdressDTO {

    private String publicPlace; //logradouro
    private String zipCode; //CEP
    private Integer number; //número
    private String city; //cidade

    private Person person; //classe pessoa

    public AdressDTO(Adress adress){
        publicPlace = adress.getPublicPlace();
        zipCode = adress.getZipCode();
        number = adress.getNumber();
        city = adress.getCity();
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
