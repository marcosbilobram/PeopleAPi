package com.attornatus.project.repositoryTest;

import com.attornatus.project.entities.Address;
import com.attornatus.project.repositories.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AddressRepositoryTest {

    @Autowired
    AddressRepository repository;

    @Test
    public void mustSaveNewAddressInDB() throws Exception {
        Address address = addressBuilder();
        repository.saveAndFlush(address);
        Optional<Address> addressInDB = repository.findById(address.getId());

        Assertions.assertTrue(addressInDB.isPresent());
        Assertions.assertEquals(addressInDB.get().getId(), address.getId());
        Assertions.assertEquals(addressInDB.get().getZipCode(), address.getZipCode());
    }

    @Test
    public void mustFindAddressInDBWithGivenId() throws Exception {
        Address address = addressBuilder();
        repository.saveAndFlush(address);
        Optional<Address> addressInDB = repository.findById(address.getId());

        Assertions.assertTrue(addressInDB.isPresent());
        Assertions.assertEquals(addressInDB.get().getId(), address.getId());
        Assertions.assertEquals(addressInDB.get().getZipCode(), address.getZipCode());
    }

    @Test
    public void mustReturnAllAddressesInDBInPage() throws Exception {
        List<Address> addressList = addressListBuilder();
        repository.saveAllAndFlush(addressList);
        Page<Address> pageList = repository.findAll(Pageable.ofSize(5));

        Assertions.assertFalse(pageList.getContent().isEmpty());
        Assertions.assertEquals(5, pageList.getContent().size());
    }

    @Test
    public void mustDeleteAddressInDbWithGivenId() throws Exception {
        Address address = addressBuilder();
        repository.saveAndFlush(address);
        repository.deleteById(address.getId());
        Optional<Address> addressSearch = repository.findById(1L);

        Assertions.assertFalse(addressSearch.isPresent());
    }

    public Address addressBuilder(){
        return Address.builder()
                .number(455)
                .street("Rua das laranjas")
                .city("Cidade das frutas")
                .zipCode("66654-552")
                .isMain(true)
                .build();
    }

    public List<Address> addressListBuilder(){
        return List.of(addressBuilder(),
                       addressBuilder(),
                       addressBuilder(),
                       addressBuilder(),
                       addressBuilder());
    }
}
