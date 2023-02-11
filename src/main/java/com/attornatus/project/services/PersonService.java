package com.attornatus.project.services;

import com.attornatus.project.DTO.PersonDTO;
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
public class PersonService {

    @Autowired
    PersonRepository personRep;

    @Autowired
    AddressRepository addressRep;

    @Autowired
    AddressService adsSer;

    public List<Person> findAll() {
        List<Person> list = personRep.findAll();

        if (list.isEmpty()) {
            throw new ObjectNotFoundException("There is no such data available");
        }

        return list;
    }

    public Person findById(Long id) throws ObjectNotFoundException {
        Optional<Person> person = personRep.findById(id);

        if (!person.isPresent()) {
            throw new ObjectNotFoundException("Person with id: " + id + " is not available");
        }

        return person.get();
    }

    public Person insert(Person person) {
        return personRep.save(person);
    }

    public Person update(Person person) {
        Person pers = findById(person.getId());
        dataUpdater(pers, person);
        return personRep.save(pers);
    }

    public void delete(Long id) {
        findById(id);
        personRep.deleteById(id);
    }

    public void setMainAddress(Long personId, Long addressID) {
        findById(personId);
        adsSer.findById(addressID);

        List<Address> listAds = adsSer.getPersonAddressesById(personId);
        Address address = null;

        for (Address ads : listAds) {
            ads.setMain(false);

            if (ads.getId() == addressID) {
                address = ads;
            }

        }

        if (address == null) {
            throw new ObjectNotFoundException("Address with id: " + addressID + " is not available");
        }

        address.setMain(true);

        addressRep.save(address);

    }

    public void insertAddress(Long personId, Address address) {
        Person person = findById(personId);
        person.getAddresses().add(address);
        address.setPerson(person);
        personRep.save(person);
    }

    public List<Person> findByName(String name) {
        List<Person> list = personRep.findByNameContainingIgnoreCase(name);

        if (list.isEmpty()) {
            throw new ObjectNotFoundException("There is no such person with name: " + name);

        }
        else {
            return list;
        }

    }

    public void dataUpdater(Person personOnDB, Person newPerson) {
        personOnDB.setName(newPerson.getName());
        personOnDB.setBirthDay(newPerson.getBirthDay());
    }

    public Person fromDTO(PersonDTO personDTO) {
        return new Person(
                personDTO.getId(),
                personDTO.getName(),
                personDTO.getBirthDay(),
                personDTO.getAddresses()
        );
    }

}
