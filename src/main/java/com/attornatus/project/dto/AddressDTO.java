package com.attornatus.project.dto;

import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressDTO {

    private Long id;
    private String street; //logradouro
    private Integer number; //número
    private String zipCode; //CEP
    private String city; //cidade
    private Boolean isMain;

    private Person person; //classe pessoa

    public AddressDTO(Address address) {
        id = address.getId();
        street = address.getStreet();
        number = address.getNumber();
        zipCode = address.getZipCode();
        city = address.getCity();
        isMain = address.getIsMain();
    }
}
