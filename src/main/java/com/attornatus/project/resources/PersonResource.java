package com.attornatus.project.resources;

import com.attornatus.project.DTO.AddressDTO;
import com.attornatus.project.DTO.PersonDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.services.AddressService;
import com.attornatus.project.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<PersonDTO>> findAll(){
        List<Person> people = personService.findAll();
        List<PersonDTO> peopleDTO = people.stream().map(p -> new PersonDTO(p)).collect(Collectors.toList());
        return ResponseEntity.ok().body(peopleDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(new PersonDTO(person));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Void> insert(@RequestBody PersonDTO personDTO){
        Person person = personService.fromDTO(personDTO);
        person = personService.insert(person);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody PersonDTO personDTO, @PathVariable Long id){
        Person person = personService.fromDTO(personDTO);
        person.setId(id);
        person = personService.update(person);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{personId}/ads/{addressId}/edit")
    public ResponseEntity<Void> setMainAddress(@PathVariable Long personId, @PathVariable Long addressId){
        personService.setMainAddress(personId, addressId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{personId}/ads/add")
    public ResponseEntity<Void> insertAddress(@PathVariable Long personId, @RequestBody AddressDTO addressDTO){
        Address ads = addressService.fromDTO(addressDTO);
        personService.insertAddress(personId, ads);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{personId}/ads")
    public ResponseEntity<List<AddressDTO>> findPersonAdresses(@PathVariable Long personId){
        List<Address> ads = addressService.getPersonAddressessById(personId);
        List<AddressDTO> list = ads.stream().map(ad -> new AddressDTO(ad)).toList();
        return ResponseEntity.ok().body(list);
    }
}
