package com.attornatus.project.services;

import com.attornatus.project.dto.AddressDTO;
import com.attornatus.project.dto.AddressEditDTO;
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

    public Address update(Long id, AddressEditDTO address) {
        Address ads = findById(id);
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

        if (!person.isPresent())
            throw new ObjectNotFoundException("Person with id: " + id + " is not available");

        Address ads = addressRep.getAddressByIsMainEqualsTrue(id);

        if (ads == null)
            throw new ObjectNotFoundException("There is no main address available in person with id: " + id);

        return ads;
    }

    public void dataUpdater(Address addressOnDB, AddressEditDTO newAddress) {
        String street = (newAddress.getStreet() != null && !newAddress.getStreet().isEmpty()
                && !newAddress.getStreet().isBlank()) ? newAddress.getStreet() : addressOnDB.getStreet();
        addressOnDB.setStreet(street);

        Integer number = (newAddress.getNumber() != null) ? newAddress.getNumber() : addressOnDB.getNumber();
        addressOnDB.setNumber(number);

        String CEP = (newAddress.getZipCode() != null && !newAddress.getZipCode().isEmpty()
                && !newAddress.getZipCode().isBlank()) ? newAddress.getZipCode() : addressOnDB.getZipCode();
        addressOnDB.setZipCode(CEP);

        String city = (newAddress.getCity() != null && !newAddress.getCity().isEmpty()
                && !newAddress.getCity().isBlank()) ? newAddress.getCity() : addressOnDB.getCity();
        addressOnDB.setCity(city);
    }

    public Address parseAddressDto(AddressDTO addressDTO) {
        return new Address(
                addressDTO.getStreet(),
                addressDTO.getNumber(),
                addressDTO.getZipCode(),
                addressDTO.getCity(),
                addressDTO.getIsMain()
        );
    }
}
