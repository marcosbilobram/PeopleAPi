package com.attornatus.project.serviceTest;

import com.attornatus.project.dto.AddressDTO;
import com.attornatus.project.dto.AddressEditDTO;
import com.attornatus.project.dto.PersonDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.repositories.AddressRepository;
import com.attornatus.project.repositories.PersonRepository;
import com.attornatus.project.services.AddressService;
import com.attornatus.project.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private PersonService personService;

    @Mock
    private AddressService addressService;

    @Test
    public void mustInsertNewPersonInDB() throws Exception {
        PersonDTO personDto = personDTOBuilder();
        Person person =  personService.parsePersonDto(personDto);
        when(personRepository.save(any(Person.class))).thenReturn(person);
        when(personService.insert(personDto)).thenReturn(person);

        Person savedPerson = personService.insert(personDto);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo(personDto.getName());
    }

    @Test
    public void mustReturnPageWithPersonList() throws Exception {
        Page<Person> personPage = new PageImpl<>(personListBuilder());
        personRepository.saveAllAndFlush(personListBuilder());
        //doNothing().when(personRepository.saveAllAndFlush(personListBuilder()));
        when(personRepository.findAll(Pageable.ofSize(5))).thenReturn(personPage);
        Page<Person> personSearch = personRepository.findAll(Pageable.ofSize(5));

        assertThat(personSearch).isNotNull();
        assertThat(personSearch).isNotEmpty();
    }

    @Test
    public void mustReturnPersonWithGivenId() throws Exception {
        Person personToSave = personBuilder();
        personToSave.setId(1L);
        personRepository.save(personToSave);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(personToSave));

        Person person = personService.findById(1L);

        assertThat(person).isNotNull();
        assertThat(person.getId()).isEqualTo(1L);
    }

    @Test
    public void mustDeletePersonWithGivenId() throws Exception {
        Person personToSave = personBuilder();
        personToSave.setId(1L);
        personRepository.save(personToSave);

        personRepository.deleteById(1L);
        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    public void mustUpdatePersonInDb() throws Exception {
        Person personToSave = personBuilder();
        personToSave.setId(1L);
        personRepository.saveAndFlush(personToSave);
        PersonDTO personDTO = personDTOBuilder();
        personDTO.setName("Socram");

        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(personToSave));
        when(personService.editPerson(personDTO, 1L))
                .thenReturn(personToSave);

        Person personEdited = personService.editPerson(personDTO, 1L);

        assertThat(personEdited.getId()).isEqualTo(personToSave.getId());
        assertThat(personEdited.getName()).isEqualTo("Socram");
    }

    @Test
    public void mustSetAMainAddressWithGivenIdAndUnsetPossibleOthers() throws Exception {
        Person personToSave = personBuilder();
        personToSave.setId(1L);
        personRepository.saveAndFlush(personToSave);
        Address address = addressBuilder();
        address.setId(1L);
        Address address2 = addressBuilder();
        address2.setId(2L);
        address2.setStreet("Rua das flores");
        address2.setIsMain(false);
        address.setPerson(personToSave);
        address2.setPerson(personToSave);
        addressRepository.saveAll(List.of(address, address2));
        personToSave.setAddresses(List.of(address, address2));
        personRepository.save(personToSave);

        when(personRepository.findById(1L)).thenReturn(Optional.of(personToSave));
        //when(addressRepository.findById(2L)).thenReturn(Optional.of(address2));
        //when(personRepository.findById(1L)).thenReturn(Optional.of(personToSave));
       //when(addressRepository.getAllByPersonId(1L)).thenReturn(List.of(address, address2));
        when(addressService.getPersonAddressesById(personToSave.getId())).thenReturn(List.of(address, address2));
        when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address2);
        when(addressService.findById(1L)).thenReturn(address);
        when(addressService.findById(2L)).thenReturn(address2);

        personService.setMainAddress(personToSave.getId(), address2.getId());
        Address addressOnDb1 = addressService.findById(1L);
        Address addressOnDb2 = addressService.findById(2L);

        assertThat(addressOnDb1).isNotNull();
        assertThat(addressOnDb2).isNotNull();
        assertThat(addressOnDb1.getIsMain()).isEqualTo(false);
        assertThat(addressOnDb2.getIsMain()).isEqualTo(true);
        //verify(personService, atLeastOnce()).setMainAddress(1L, 2L);
    }

    @Test
    public void mustReturnUserWithGivenName() throws Exception {
        List<Person> personsToSave = personListBuilder();
        Person marcos = personsToSave.get(0);
        personRepository.saveAllAndFlush(personsToSave);

        when(personRepository.findByNameContainingIgnoreCase("Marcos")).thenReturn(Optional.of(List.of(marcos)));

        List<Person> personSearch = personService.findByName("Marcos");

        assertThat(personSearch).isNotNull();
        assertThat(personSearch).isNotEmpty();
        assertThat(personSearch.get(0).getName()).isEqualTo(marcos.getName());
    }

    @Test
    public void mustInsertNewAddressInPersonData() throws Exception {
        Person personToSave = personBuilder();
        personToSave.setId(1L);
        personRepository.saveAndFlush(personToSave);
        Address address = addressBuilder();
        addressRepository.saveAndFlush(address);
        //address.setId(1L);
        personToSave.setAddresses(List.of(address));
        personRepository.saveAndFlush(personToSave);
        address.setPerson(personToSave);
        addressRepository.saveAndFlush(address);
        AddressDTO addressDTO = addressDTOBuilder();

        when(addressService.parseAddressDto(addressDTO)).thenReturn(address);
        when(personRepository.findById(personToSave.getId())).thenReturn(Optional.of(personToSave));
        when(personRepository.saveAndFlush(Mockito.any(Person.class))).thenReturn(personToSave);
        when(addressRepository.saveAndFlush(Mockito.any(Address.class))).thenReturn(address);

        personService.insertAddress(personToSave.getId(), addressDTO);

        List<Address> personAddresses = addressService.getPersonAddressesById(1L);

        assertThat(personAddresses).isNotNull();
        assertThat(personAddresses).isNotEmpty();
        assertThat(personAddresses.size()).isGreaterThan(1);
        assertThat(personAddresses.size()).isGreaterThan(1);
        assertThat(personAddresses.get(1).getZipCode()).isEqualTo(addressDTO.getZipCode());

    }

    @Test
    public void mustEditAddressWithGivenId() throws Exception {
        Person personToSave = personBuilder();
        personToSave.setId(1L);
        personRepository.saveAndFlush(personToSave);
        Address address = addressBuilder();
        address.setId(1L);
        addressRepository.saveAndFlush(address);
        personToSave.setAddresses(List.of(address));
        personRepository.saveAndFlush(personToSave);
        address.setPerson(personToSave);
        addressRepository.saveAndFlush(address);
        AddressEditDTO addressEditDTO = AddressEditDTO.builder().number(45).street("Rua").build();

        when(personRepository.findById(1L)).thenReturn(Optional.of(personToSave));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(address)).thenReturn(address);

        personService.editAddress(1L, 1L, addressEditDTO);

        Address address1 = addressRepository.findById(1L).get();

        assertThat(address1.getStreet()).isEqualTo("Rua");

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

    public Address addressBuilder() throws Exception {
        return Address.builder()
                .street("Rua 11")
                .number(456)
                .zipCode("0545-255")
                .city("Gottan City")
                .isMain(true)
                .build();
    }

    public AddressDTO addressDTOBuilder() throws Exception {
        return AddressDTO.builder()
                .street("Rua 12")
                .number(654)
                .zipCode("12345-788")
                .city("Gottan Town")
                .isMain(false)
                .build();
    }
}
