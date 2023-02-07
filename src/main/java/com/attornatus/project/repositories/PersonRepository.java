package com.attornatus.project.repositories;

import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {



}
