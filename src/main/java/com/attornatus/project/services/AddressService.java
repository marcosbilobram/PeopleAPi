package com.attornatus.project.services;

import com.attornatus.project.dto.AddressDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.exceptions.ObjectNotFoundException;
import com.attornatus.project.repositories.AddressRepository;
import com.attornatus.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRep;

    @Autowired
    PersonRepository personRep;

    public List<Address> findAll() {
        List<Address> list = addressRep.findAll();

        if (list.isEmpty()) {
            throw new ObjectNotFoundException("There is no such data available");
        }
        return list;
    }

    public Address findById(Long id) {
        Optional<Address> ads = addressRep.findById(id);

        if (!ads.isPresent()) {
            throw new ObjectNotFoundException("Address with id: " + id + " is not available");
        }
        return ads.get();
    }

    public Address insert(Address address) {
        return addressRep.save(address);
    }

    public Address update(Address address) {
        Address ads = findById(address.getId());
        dataUpdater(ads, address);
        return addressRep.save(ads);
    }

    public void delete(Long id) {
        findById(id);
        addressRep.deleteById(id);
    }

    public List<Address> getPersonAddressesById(Long id) {
        Optional<Person> person = personRep.findById(id);

        if (!person.isPresent()) {
            throw new ObjectNotFoundException("Person with id: " + id + " is not available");
        }

        List<Address> ads = addressRep.getAllByPersonId(id);

        if (ads.isEmpty()) {
            throw new ObjectNotFoundException("There is no address available in person with id: " + id);
        }
        return ads;
    }

    public Address getMainAddress(Long id) {
        Optional<Person> person = personRep.findById(id);

        if (!person.isPresent()) {
            throw new ObjectNotFoundException("Person with id: " + id + " is not available");
        }

        Address ads = addressRep.getAddressByIsMainEqualsTrue(id);

        if (ads == null) {
            throw new ObjectNotFoundException("There is no main address available in person with id: " + id);
        }
        return ads;
    }

    public void dataUpdater(Address addressOnDB, Address newAddress) {
        addressOnDB.setPublicPlace(newAddress.getPublicPlace());
        addressOnDB.setNumber(newAddress.getNumber());
        addressOnDB.setZipCode(newAddress.getZipCode());
        addressOnDB.setCity(newAddress.getCity());
    }

    public Address fromDTO(AddressDTO addressDTO) {
        return new Address(
                addressDTO.getId(),
                addressDTO.getPublicPlace(),
                addressDTO.getNumber(),
                addressDTO.getZipCode(),
                addressDTO.getCity(),
                addressDTO.getPerson(),
                addressDTO.getMain()
        );
    }
}
