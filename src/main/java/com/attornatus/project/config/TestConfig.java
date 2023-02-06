package com.attornatus.project.config;

import com.attornatus.project.repositories.AdressRepository;
import com.attornatus.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AdressRepository adressRepository;


}
