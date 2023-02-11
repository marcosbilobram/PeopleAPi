package com.attornatus.project.resources;

import com.attornatus.project.DTO.AddressDTO;
import com.attornatus.project.DTO.PersonDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.exceptions.InvalidPropertyException;
import com.attornatus.project.exceptions.ObjectNotFoundException;
import com.attornatus.project.services.AddressService;
import com.attornatus.project.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonResource {

    @Autowired
    PersonService personService;

    @Autowired
    AddressService addressService;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        List<Person> people = personService.findAll();
        List<PersonDTO> peopleDTO = people.stream().map(p -> new PersonDTO(p)).collect(Collectors.toList());
        return ResponseEntity.ok().body(peopleDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id) throws ObjectNotFoundException {
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(new PersonDTO(person));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Void> insert(@RequestBody PersonDTO personDTO) {
        try {
            Person person = personService.fromDTO(personDTO);
            person = personService.insert(person);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }
        catch (DataIntegrityViolationException e) {
            if (personDTO.getName() == null || personDTO.getName().length() > 60) {
                throw new InvalidPropertyException("Invalid property given on request", "Person", "Name");
            }

            if (personDTO.getBirthDay() == null) {
                throw new InvalidPropertyException("Invalid property given on request", "Person", "Birthday");
            }

        }
        return null;
    }

    @PutMapping(value = "/{id}/edit")
    public ResponseEntity<Void> update(@RequestBody PersonDTO personDTO, @PathVariable Long id) {
        try {
            Person person = personService.fromDTO(personDTO);
            person.setId(id);
            person = personService.update(person);
            return ResponseEntity.noContent().build();
        }
        catch (DataIntegrityViolationException e) {
            if (personDTO.getName() == null || personDTO.getName().length() > 60) {
                throw new InvalidPropertyException("Invalid property given on request", "Person", "Name");
            }

            if (personDTO.getBirthDay() == null) {
                throw new InvalidPropertyException("Invalid property given on request", "Person", "Birthday");
            }

        }
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<PersonDTO>> findPersonByName(@RequestParam("name") String name) {
        List<PersonDTO> list = personService.findByName(name).stream().map(p -> new PersonDTO(p)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/{personId}/ads/{addressId}/edit")
    public ResponseEntity<Void> setMainAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        personService.setMainAddress(personId, addressId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{personId}/ads/add")
    public ResponseEntity<Void> insertPersonAddress(@PathVariable Long personId, @RequestBody AddressDTO addressDTO) {
        try {
            personService.findById(personId);
            Address ads = addressService.fromDTO(addressDTO);
            personService.insertAddress(personId, ads);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ads.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }
        catch (DataIntegrityViolationException e) {
            if (addressDTO.getPublicPlace() == null) {
                throw new InvalidPropertyException("Invalid property given on request", "Address", "PublicPlace");
            }

            if (addressDTO.getNumber() == null) {
                throw new InvalidPropertyException("Invalid property given on request", "Address", "Number");
            }

            if (addressDTO.getZipCode() == null || addressDTO.getZipCode().length() > 8) {
                throw new InvalidPropertyException("Invalid property given on request", "Address", "ZipCode");
            }

            if (addressDTO.getCity() == null) {
                throw new InvalidPropertyException("Invalid property given on request", "Address", "City");
            }

        }

        return null;
    }

    @GetMapping(value = "/{personId}/ads")
    public ResponseEntity<List<AddressDTO>> findPersonAdresses(@PathVariable Long personId) {
        List<Address> ads = addressService.getPersonAddressesById(personId);
        List<AddressDTO> list = ads.stream().map(ad -> new AddressDTO(ad)).toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{personId}/ads/main")
    public ResponseEntity<AddressDTO> findMainAddress(@PathVariable Long personId) {
        Address address = addressService.getMainAddress(personId);
        return ResponseEntity.ok().body(new AddressDTO(address));
    }
}
