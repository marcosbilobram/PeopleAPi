package com.attornatus.project.repositories;

import com.attornatus.project.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Page<Person> findAll(Pageable pageable);

    Optional<List<Person>> findByNameContainingIgnoreCase(String name);

}
