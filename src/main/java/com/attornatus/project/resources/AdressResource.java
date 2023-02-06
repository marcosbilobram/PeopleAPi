package com.attornatus.project.resources;


import com.attornatus.project.services.AdressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdressResource {

    @Autowired
    AdressService adressService;

}
