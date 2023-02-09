package com.attornatus.project.config;

import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.repositories.AddressRepository;
import com.attornatus.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("Test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Person person1 = new Person(1L, "Marcos", sdf.parse("14/02/2004"), null );

        Address address1 = new Address(3L, "Rua do avestruz", 100, "68544-225","Cidade das cidades",null, false);

        Person person2 = new Person(2L, "Jeff Bezos", sdf.parse("25/08/1981"), null );

        Address address2 = new Address(1L, "Rua das lagoas", 45, "0006-554","Osasco",null, false);

        Person person3 = new Person(3L, "Mark Zuckerberg", sdf.parse("28/08/1995"), null );

        Address address3 = new Address(2L, "Rua dos patos", 498, "0006-255","Osasco",null, false);



        person1.setAddresses(List.of(address1));
        address1.setPerson(person1);

        person2.setAddresses(List.of(address2));
        address2.setPerson(person2);

        person3.setAddresses(List.of(address3));
        address3.setPerson(person3);

        personRepository.saveAll(Arrays.asList(person1, person2, person3));
        addressRepository.saveAll(Arrays.asList(address1, address2, address3));

    }

    //TODO : find person addresses
    //TODO : testes crud


}