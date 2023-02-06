package com.attornatus.project.services;

import com.attornatus.project.repositories.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdressService {

    @Autowired
    AdressRepository adressRep;
}
