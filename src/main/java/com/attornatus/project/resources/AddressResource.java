package com.attornatus.project.resources;


import com.attornatus.project.DTO.AddressDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.exceptions.InvalidPropertyException;
import com.attornatus.project.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        try{
            Address ads = addressService.fromDTO(addressDTO);
            ads = addressService.insert(ads);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(ads.getId()).toUri();
            return ResponseEntity.created(uri).build();

        }catch (DataIntegrityViolationException e){
            if(addressDTO.getPublicPlace() == null ){
                throw new InvalidPropertyException("Invalid property given on request","Address","PublicPlace");
            }
            if(addressDTO.getNumber() == null){
                throw new InvalidPropertyException("Invalid property given on request","Address","Number");
            }
            if(addressDTO.getZipCode() == null || addressDTO.getZipCode().length() > 8){
                throw new InvalidPropertyException("Invalid property given on request","Address","ZipCode");
            }
            if(addressDTO.getCity() == null){
                throw new InvalidPropertyException("Invalid property given on request","Address","City");
            }
        }

        return null;
    }

    @PutMapping(value = "/{id}/edit")
    public ResponseEntity<Void> update(@RequestBody AddressDTO addressDTO, @PathVariable Long id){
        try {
            Address ads = addressService.fromDTO(addressDTO);
            ads.setId(id);
            ads = addressService.update(ads);
            return ResponseEntity.noContent().build();
        }catch (DataIntegrityViolationException e){
            if(addressDTO.getPublicPlace() == null || addressDTO.getPublicPlace().length() > 45){
                throw new InvalidPropertyException("Invalid property given on request","Address","PublicPlace");
            }
            if(addressDTO.getNumber() == null){
                throw new InvalidPropertyException("Invalid property given on request","Address","Number");
            }
            if(addressDTO.getZipCode() == null || addressDTO.getZipCode().length() > 8){
                throw new InvalidPropertyException("Invalid property given on request","Address","ZipCode");
            }
            if(addressDTO.getCity() == null){
                throw new InvalidPropertyException("Invalid property given on request","Address","City");
            }
        }
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
