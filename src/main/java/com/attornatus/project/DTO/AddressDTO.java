package com.attornatus.project.DTO;

import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;

public class AddressDTO {

    private Long id;
    private String publicPlace; //logradouro
    private Integer number; //número
    private String zipCode; //CEP
    private String city; //cidade
    private Boolean isMain;

    private Person person; //classe pessoa

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        id = address.getId();
        publicPlace = address.getPublicPlace();
        number = address.getNumber();
        zipCode = address.getZipCode();
        city = address.getCity();
        isMain = address.getMain();
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
