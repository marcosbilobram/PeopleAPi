package com.attornatus.project.repositoryTest;

import com.attornatus.project.entities.Person;
import com.attornatus.project.repositories.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    public void mustSaveNewPersonInDB() throws Exception {
        Person person = personBuilder();
        repository.saveAndFlush(person);
        Optional<Person> personInDB = repository.findById(person.getId());

        Assertions.assertTrue(personInDB.isPresent());
        Assertions.assertEquals(personInDB.get().getId(), person.getId());
        Assertions.assertEquals(personInDB.get().getName(), person.getName());
        Assertions.assertEquals(personInDB.get().getBirthDay(), person.getBirthDay());
    }

    @Test
    public void mustFindPersonInDBWithGivenId() throws Exception {
        Person person = personBuilder();
        repository.saveAndFlush(person);
        Optional<Person> personInDB = repository.findById(person.getId());

        Assertions.assertTrue(personInDB.isPresent());
        Assertions.assertEquals(personInDB.get().getId(), person.getId());
        Assertions.assertEquals(personInDB.get().getName(), person.getName());
        Assertions.assertEquals(personInDB.get().getBirthDay(), person.getBirthDay());
    }

    @Test
    public void mustReturnAllPeopleInDBInPage() throws Exception {
        List<Person> personList = personListBuilder();
        repository.saveAllAndFlush(personList);
        Page<Person> pageList = repository.findAll(Pageable.ofSize(5));

        Assertions.assertFalse(pageList.getContent().isEmpty());
        Assertions.assertEquals(5, pageList.getContent().size());
    }

    @Test
    public void mustReturnPeopleListWithGivenName() throws Exception {
        List<Person> personList = personListBuilder();
        repository.saveAllAndFlush(personList);

        List<Person> personListInDB = repository.findByNameContainingIgnoreCase("Marcos").get();

        Assertions.assertFalse(personListInDB.isEmpty());
        Assertions.assertTrue(personListInDB.size() > 1);
        Assertions.assertEquals("Marcos", personListInDB.get(0).getName());
        Assertions.assertEquals("Marcos", personListInDB.get(1).getName());
    }

    @Test
    public void mustDeletePersonInDbWithGivenId() throws Exception {
        Person person = personBuilder();
        repository.saveAndFlush(person);
        repository.deleteById(person.getId());
        Optional<Person> personSearch = repository.findById(1L);

        Assertions.assertFalse(personSearch.isPresent());
    }

    public Person personBuilder() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return Person.builder().name("Marcos").birthDay(formatter.parse("14/02/2004")).build();
    }

    public List<Person> personListBuilder() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return List.of(
                Person.builder().name("Marcos").birthDay(formatter.parse("14/02/2004")).build(),
                Person.builder().name("Elon Musk").birthDay(formatter.parse("15/02/2004")).build(),
                Person.builder().name("Jeff Bezos").birthDay(formatter.parse("16/02/2004")).build(),
                Person.builder().name("Neil Degrase").birthDay(formatter.parse("17/02/2004")).build(),
                Person.builder().name("Marcos").birthDay(formatter.parse("18/03/2004")).build(),
                Person.builder().name("Sergio Casani").birthDay(formatter.parse("19/04/2004")).build());
    }
}
