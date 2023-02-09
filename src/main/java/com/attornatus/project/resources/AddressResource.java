package com.attornatus.project.resources;


import com.attornatus.project.DTO.AddressDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
public class AddressResource {

    @Autowired
    AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll(){
        List<Address> addresses = addressService.findAll();
        List<AddressDTO> addressDTOS = addresses.stream().map(ad -> new AddressDTO(ad)).collect(Collectors.toList());
        return ResponseEntity.ok().body(addressDTOS);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id){
        Address ads = addressService.findById(id);
        return ResponseEntity.ok().body(new AddressDTO(ads));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody AddressDTO addressDTO){
        Address ads = addressService.fromDTO(addressDTO);
        ads = addressService.insert(ads);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(ads.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody AddressDTO userDTO, @PathVariable Long id){
        Address ads = addressService.fromDTO(userDTO);
        ads.setId(id);
        ads = addressService.update(ads);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
