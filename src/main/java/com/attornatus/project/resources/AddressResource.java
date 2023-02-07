package com.attornatus.project.resources;


import com.attornatus.project.services.AddressService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController("/address")
public class AddressResource {

    @Autowired
    AddressService addressService;



    @PutMapping(value = "/{personId}")
    public ResponseEntity<Void> setMainAddress(@PathVariable Long personId, @RequestAttribute Long adressId){
        addressService.setMainAddress(personId, adressId);
        return ResponseEntity.noContent().build();
    }


}
