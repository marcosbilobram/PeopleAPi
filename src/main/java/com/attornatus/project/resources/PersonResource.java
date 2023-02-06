package com.attornatus.project.resources;

import com.attornatus.project.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonResource {

    @Autowired
    PersonService personService;
}
