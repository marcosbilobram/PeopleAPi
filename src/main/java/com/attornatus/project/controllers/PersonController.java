package com.attornatus.project.controllers;

import com.attornatus.project.dto.AddressDTO;
import com.attornatus.project.dto.PersonDataReturnDTO;
import com.attornatus.project.dto.PersonDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.exceptions.InvalidPropertyException;
import com.attornatus.project.exceptions.ObjectNotFoundException;
import com.attornatus.project.services.AddressService;
import com.attornatus.project.services.PersonService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    AddressService addressService;

    @GetMapping("/all")
    public ResponseEntity<Page<PersonDataReturnDTO>> findAll(@ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        Page<Person> people = personService.findAll(pageable);
        return ResponseEntity.ok().body(people.map(PersonDataReturnDTO::new));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDataReturnDTO> findById(@PathVariable Long id) throws ObjectNotFoundException {
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(new PersonDataReturnDTO(person));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Void> insert(@RequestBody @Valid PersonDTO personDTO) {
        personService.insert(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}/edit")
    public ResponseEntity<Void> update(@RequestBody @Valid PersonDTO personDTO, @PathVariable Long id) {
        personService.update(personDTO, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/name")
    public ResponseEntity<List<PersonDataReturnDTO>> findPersonByName(@RequestParam("name") String name) {
        List<PersonDataReturnDTO> list = personService.findByName(name).stream().map(p -> new PersonDataReturnDTO(p)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/{personId}/ads/{addressId}/edit")
    public ResponseEntity<Void> setMainAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        personService.setMainAddress(personId, addressId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{personId}/ads/add")
    public ResponseEntity<Void> insertPersonAddress(@PathVariable Long personId, @RequestBody @Valid AddressDTO addressDTO) {
            personService.insertAddress(personId, addressDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{personId}/address")
    public ResponseEntity<List<AddressDTO>> findPersonAdresses(@PathVariable Long personId) {
        List<Address> ads = addressService.getPersonAddressesById(personId);
        List<AddressDTO> list = ads.stream().map(ad -> new AddressDTO(ad)).toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{personId}/address/main")
    public ResponseEntity<AddressDTO> findMainAddress(@PathVariable Long personId) {
        Address address = addressService.getMainAddress(personId);
        return ResponseEntity.ok().body(new AddressDTO(address));
    }
}
