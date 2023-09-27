package com.attornatus.project.config;

import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.repositories.AddressRepository;
import com.attornatus.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataBaseSeeder implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Person person1 = new Person("Marcos", sdf.parse("14/02/2004"), null);

        Address address1 = Address.builder().street("Rua 12").number(484).zipCode("0123-456").city("Cidade grande").isMain(false).build();

        Address address2 = Address.builder().street("Rua 11").number(456).zipCode("0545-255").city("Gottan City").isMain(true).build();

        Person person2 = new Person("Jeff Bezos", sdf.parse("25/08/1981"));

        Address address3 = Address.builder().street("Rua 10").number(475).zipCode("0555-656").city("Cidade das esmeraldas").isMain(false).build();

        Address address4 = Address.builder().street("Rua 9").number(478).zipCode("0555-434").city("Hong Kong").isMain(true).build();

        person1.setAddresses(List.of(address1, address2));
        address1.setPerson(person1);
        address2.setPerson(person1);

        person2.setAddresses(List.of(address3, address4));
        address3.setPerson(person2);
        address4.setPerson(person2);

        personRepository.saveAll(Arrays.asList(person1, person2));
        addressRepository.saveAll(Arrays.asList(address1, address2, address3));

    }

}