package com.attornatus.project.services;

import com.attornatus.project.DTO.AddressDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.repositories.AddressRepository;
import com.attornatus.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRep;

    //set main address
    public void setMainAddress(Long personId, Long addressID){
        List<Address> listAds = addressRep.getAddressesByPersonId(personId);
        for(Address ads : listAds){
            ads.setMain(false);
        }
        Address ads = addressRep.findById(addressID).get();
        ads.setMain(true);
    } //ID de pessoa



    public Address fromDTO(AddressDTO addressDTO){
        return new Address(addressDTO.getId(), addressDTO.getPublicPlace(), addressDTO.getNumber(),
                            addressDTO.getZipCode(), addressDTO.getCity(), addressDTO.getPerson());
    }
}
