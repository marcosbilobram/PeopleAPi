package com.attornatus.project.dto;

import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonDataReturnDTO {

    private String name; // Nome
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay; // Data de nascimento

    private List<Address> addresses = new ArrayList<>(); // Endereços

    public PersonDataReturnDTO(Person person) {
        name = person.getName();
        birthDay = person.getBirthDay();
        addresses = person.getAddresses();
    }
}
