package com.attornatus.project.services;

import com.attornatus.project.DTO.PersonDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.repositories.AddressRepository;
import com.attornatus.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRep;

    @Autowired
    AddressRepository addressRep;

    public List<Person> findAll(){
        return personRep.findAll();
    }

    public Person findById(Long id){
        return personRep.findById(id).get();
    }

    public Person insert(Person person){
        return personRep.save(person);
    }

    public Person update(Person person){
        Person pers = findById(person.getId());
        dataUpdater(pers, person);
        return personRep.save(pers);
    }

    public void delete(Long id){
        personRep.deleteById(id);
    }

    public void setMainAddress(Long personId, Long addressID){
        List<Address> listAds = addressRep.getAllByPersonId(personId);
        for(Address ads : listAds){
            ads.setMain(false);
        }
        Address ads = addressRep.findById(addressID).get();
        ads.setMain(true);

        addressRep.save(ads);
    }

    public void insertAddress(Long personId, Address address){
        Person person = findById(personId);
        person.getAddresses().add(address);
        address.setPerson(person);
        personRep.save(person);
    }

    public void dataUpdater(Person personOnDB, Person newPerson) {
        personOnDB.setName(newPerson.getName());
        personOnDB.setBirthDay(newPerson.getBirthDay());
    }

    public Person fromDTO(PersonDTO personDTO){
        return new Person(personDTO.getId(), personDTO.getName(), personDTO.getBirthDay(), personDTO.getAddresses());
    }
}
