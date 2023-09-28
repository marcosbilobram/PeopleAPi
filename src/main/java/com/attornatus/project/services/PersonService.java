package com.attornatus.project.services;

import com.attornatus.project.dto.AddressDTO;
import com.attornatus.project.dto.AddressEditDTO;
import com.attornatus.project.dto.PersonDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.exceptions.InvalidPropertyException;
import com.attornatus.project.exceptions.ObjectNotFoundException;
import com.attornatus.project.repositories.AddressRepository;
import com.attornatus.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRep;

    @Autowired
    AddressRepository addressRep;

    @Autowired
    AddressService addressService;

    public Page<Person> findAll(Pageable pageable) {
        if (personRep.findAll(pageable).isEmpty()){
            throw new ObjectNotFoundException("No such data found");
        }
        else return personRep.findAll(pageable);
    }

    public Person findById(Long id) throws ObjectNotFoundException {
        return personRep.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Person with id " + id + " not found"));
    }

    public Person insert(PersonDTO personDTO) {
        if(personDTO.getBirthDay().after(Calendar.getInstance().getTime())){
            throw new InvalidPropertyException("Invalid date", "Person","Birthday");
        }
        Person person = parsePersonDto(personDTO);
        try{
            return personRep.save(person);
        }catch (Exception e) {
            throw new DataIntegrityViolationException("Error trying to save new person");
        }

    }

    public Person update(PersonDTO personDto, Long id) {
        Person personInDB = findById(id);
        dataUpdater(personInDB, personDto);
        try{
            return personRep.save(personInDB);
        }catch (Exception e) {
            throw new DataIntegrityViolationException("Error trying to save new person");
        }
    }

    public void delete(Long id) {
        findById(id);
        personRep.deleteById(id);
    }

    public void setMainAddress(Long personId, Long addressID) {
        findById(personId);
        addressService.findById(addressID);

        List<Address> listAds = addressService.getPersonAddressesById(personId);
        Address address = null;

        for (Address ads : listAds) {
            ads.setIsMain(false);

            if (ads.getId() == addressID) {
                address = ads;
            }

        }

        if (address == null) {
            throw new ObjectNotFoundException("Address with id: " + addressID + " is not available");
        }

        address.setIsMain(true);

        addressRep.save(address);

    }

    public void insertAddress(Long personId, AddressDTO addressDTO) {
        Address address = addressService.parseAddressDto(addressDTO);
        Person person = findById(personId);
        person.getAddresses().add(address);
        personRep.saveAndFlush(person);
        address.setPerson(person);
        addressRep.saveAndFlush(address);
    }

    public List<Person> findByName(String name) {
        return personRep.findByNameContainingIgnoreCase(name)
                .orElseThrow(()-> new ObjectNotFoundException("No person with name".concat(name).concat("found")));
    }

    public void dataUpdater(Person personOnDB, PersonDTO newPerson) {
        personOnDB.setName(newPerson.getName());
        personOnDB.setBirthDay(newPerson.getBirthDay());
    }

    public Person parsePersonDto(PersonDTO personDTO) {
        return new Person(
                personDTO.getName(),
                personDTO.getBirthDay()
        );
    }

    public void editAddress(Long personId, Long addressId, AddressEditDTO addressEditDTO) {
        Person person = findById(personId);

        for(Address ads : person.getAddresses()){
            if (ads.getId().equals(addressId)){
                addressService.update(ads.getId(), addressEditDTO);
            } else
                throw new ObjectNotFoundException((
                        "User ")
                        .concat(person.getName())
                        .concat(" doesn't have address with id ")
                        .concat(addressId.toString()));
        }
    }
}
