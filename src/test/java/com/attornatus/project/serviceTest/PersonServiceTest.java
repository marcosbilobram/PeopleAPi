package com.attornatus.project.serviceTest;

import com.attornatus.project.dto.PersonDTO;
import com.attornatus.project.entities.Person;
import com.attornatus.project.repositories.PersonRepository;
import com.attornatus.project.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    public void mustInsertNewPersonInDB() throws Exception {
        PersonDTO personDto = personDTOBuilder();
        Person person =  personService.parsePersonDto(personDto);

        when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        Person savedPerson = personService.insert(personDto);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo(personDto.getName());

    }

    @Test
    public void mustReturnPageWithPersonList() throws Exception {
        Page<Person> personPage = Mockito.any();
        when(personRepository.findAll(Pageable.ofSize(5))).thenReturn(personPage);

        Page<Person> personSearch = personRepository.findAll(Pageable.ofSize(5));

        assertThat(personSearch).isNotNull();
        assertThat(personSearch).isNotEmpty();
    }

    public Person personBuilder() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return Person.builder().name("Marcos").birthDay(formatter.parse("14/02/2004")).build();
    }

    public PersonDTO personDTOBuilder() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return PersonDTO.builder().name("Marcos").birthDay(formatter.parse("14/02/2004")).build();
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
