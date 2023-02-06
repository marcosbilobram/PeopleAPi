package com.attornatus.project.entities;

import java.io.Serializable;
import java.util.*;

public class Person implements Serializable {
//Classe de Pessoa
    private String name; //Nome
    private Date birthDay; //Data de nascimento

    private List<Adress> adresses = new ArrayList<>(); //Endereços

    public Person(String name, Date birthDay, List<Adress> adresses) {
        this.name = name;
        this.birthDay = birthDay;
        this.adresses = adresses;
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

    public List<Adress> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adress> adresses) {
        this.adresses = adresses;
    }
}
