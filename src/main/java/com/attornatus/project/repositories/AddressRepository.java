package com.attornatus.project.repositories;

import com.attornatus.project.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    //@Query(value = "SELECT ad  FROM Address ad WHERE ad.person.id = ?1", nativeQuery = false)
    List<Address> getAllByPersonId(Long personId);
}
