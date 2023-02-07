package com.attornatus.project.repositories;

import com.attornatus.project.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT * FROM tb_address ad WHERE ad.person_id = ?1", nativeQuery = true)
    List<Address> getAddressesByPersonId(Long personId);
}
