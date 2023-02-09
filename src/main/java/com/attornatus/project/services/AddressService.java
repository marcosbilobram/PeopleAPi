package com.attornatus.project.services;

import com.attornatus.project.DTO.AddressDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRep;

    public List<Address> findAll(){
        return addressRep.findAll();
    }

    public Address findById(Long id){
        return addressRep.findById(id).get();
    }

    public Address insert(Address address){
        return addressRep.save(address);
    }

    public Address update(Address address){
        Address ads = findById(address.getId());
        dataUpdater(ads, address);
        return addressRep.save(ads);
    }

    public void delete(Long id){
        addressRep.deleteById(id);
    }

    public void dataUpdater(Address addressOnDB, Address newAddress){
        addressOnDB.setPublicPlace(newAddress.getPublicPlace());
        addressOnDB.setNumber(newAddress.getNumber());
        addressOnDB.setZipCode(newAddress.getZipCode());
        addressOnDB.setCity(newAddress.getCity());
    }

    public Address fromDTO(AddressDTO addressDTO){
        return new Address(addressDTO.getId(), addressDTO.getPublicPlace(), addressDTO.getNumber(),
                            addressDTO.getZipCode(), addressDTO.getCity(), addressDTO.getPerson(), addressDTO.getMain());
    }

    public List<Address> getPersonAddressessById(Long id){
        return addressRep.getAllByPersonId(id);
    }
}
