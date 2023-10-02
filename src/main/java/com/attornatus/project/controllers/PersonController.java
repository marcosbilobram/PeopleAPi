package com.attornatus.project.controllers;

import com.attornatus.project.dto.AddressDTO;
import com.attornatus.project.dto.AddressEditDTO;
import com.attornatus.project.dto.PersonDataReturnDTO;
import com.attornatus.project.dto.PersonDTO;
import com.attornatus.project.entities.Address;
import com.attornatus.project.entities.Person;
import com.attornatus.project.exceptions.ObjectNotFoundException;
import com.attornatus.project.services.AddressService;
import com.attornatus.project.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    AddressService addressService;

    @Operation(
            summary = "FindAll",
            description = "Retorna em modo de paginação os dados de todas as pessoas cadastradas no banco"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa retornada com sucesso",
            content = {@Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Person List",
                            value =
                                """
                                {
                                    "content": [
                                        {
                                            "name": "Marcos",
                                            "birthDay": "14/02/2004",
                                            "addresses": [
                                                {
                                                    "id": 1,
                                                    "street": "Rua 12",
                                                    "number": 484,
                                                    "zipCode": "0123-456",
                                                    "city": "Cidade grande",
                                                    "isMain": false
                                                },
                                                {
                                                    "id": 2,
                                                    "street": "Rua 11",
                                                    "number": 456,
                                                    "zipCode": "0545-255",
                                                    "city": "Gottan City",
                                                    "isMain": true
                                                }
                                            ]
                                        },
                                        {
                                            "name": "Jeff Bezos",
                                            "birthDay": "25/08/1981",
                                            "addresses": [
                                                {
                                                    "id": 3,
                                                    "street": "Rua 10",
                                                    "number": 475,
                                                    "zipCode": "0555-656",
                                                    "city": "Cidade das esmeraldas",
                                                    "isMain": false
                                                },
                                                {
                                                    "id": 4,
                                                    "street": "Rua 9",
                                                    "number": 478,
                                                    "zipCode": "0555-434",
                                                    "city": "Hong Kong",
                                                    "isMain": true
                                                }
                                            ]
                                        }
                                    ],
                                    "pageable": {
                                        "pageNumber": 0,
                                        "pageSize": 5,
                                        "sort": {
                                            "empty": true,
                                            "sorted": false,
                                            "unsorted": true
                                        },
                                        "offset": 0,
                                        "paged": true,
                                        "unpaged": false
                                    },
                                    "last": true,
                                    "totalElements": 2,
                                    "totalPages": 1,
                                    "size": 5,
                                    "number": 0,
                                    "sort": {
                                        "empty": true,
                                        "sorted": false,
                                        "unsorted": true
                                    },
                                    "first": true,
                                    "numberOfElements": 2,
                                    "empty": false
                                }
                                        """)
            })}),
            @ApiResponse(responseCode = "404", description = "Pesquisa sem retorno de dados",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "ObjectNotFoundException",
                                    value = """
                                            {
                                              "status": "NOT_FOUND",
                                              "message": "No such data"
                                            }
                                            """
                            )
                    })})
    })
    @GetMapping("/all")
    public ResponseEntity<Page<PersonDataReturnDTO>> findAll(@ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        Page<Person> people = personService.findAll(pageable);
        return ResponseEntity.ok().body(people.map(PersonDataReturnDTO::new));
    }

    @Operation(
            summary = "Pesquisa por ID",
            description = "Retorna a pessoa registrada com o ID fornecido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa retornada com sucesso",
            content = {@Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "Person data",
                            value = """
                                {
                                    "name": "Jeff Bezos",
                                    "birthDay": "25/08/1981",
                                    "addresses": [
                                        {
                                            "id": 3,
                                            "street": "Rua 10",
                                            "number": 475,
                                            "zipCode": "0555-656",
                                            "city": "Cidade das esmeraldas",
                                            "isMain": false
                                        },
                                        {
                                            "id": 4,
                                            "street": "Rua 9",
                                            "number": 478,
                                            "zipCode": "0555-434",
                                            "city": "Hong Kong",
                                            "isMain": true
                                        }
                                    ]
                                }
                                    """)
            })}),

            @ApiResponse(responseCode = "404", description = "Pesquisa sem retorno de dados",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "ObjectNotFoundException",
                                    value = """
                                            {
                                              "status": "NOT_FOUND",
                                              "message": "No such data"
                                            }
                                        """)
                    })})
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDataReturnDTO> findById(@PathVariable Long id) throws ObjectNotFoundException {
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(new PersonDataReturnDTO(person));
    }

    @Operation(
            summary = "Inserir pessoa",
            description = "Insere a nova pessoa no banco"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inserção realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "DataIntegrityViolationException",
                                    value = """
                                            {
                                              "status": "BAD_REQUEST",
                                              "message": "Error message"
                                            }
                                            """)
                    })})
    })
    @PostMapping(value = "/add")
    public ResponseEntity<Void> insert(@RequestBody @Valid PersonDTO personDTO) {
        personService.insert(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Editar dados de uma pessoa",
            description = "Insere novo endereço para pessoa no banco"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edição realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "DataIntegrityViolationException",
                                    value = """
                                            {
                                              "status": "BAD_REQUEST",
                                              "message": "Error message"
                                            }
                                            """)
                    })})
    })
    @PutMapping(value = "/{id}/edit")
    public ResponseEntity<Void> editPerson(@RequestBody @Valid PersonDTO personDTO, @PathVariable Long id) {
        personService.editPerson(personDTO, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Deletar pessoa",
            description = "Deleta a pessoa no banco que possui o ID informado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleção realizada com sucesso")})
            @ApiResponse(responseCode = "404", description = "Pessoa com id informado não existe",
            content = {@Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "ObjectNotFoundException",
                            value = """
                                    {
                                      "status": "NOT_FOUND",
                                      "message": "No such data"
                                    }
                                """)
            })})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Pesquisa por nome da pessoa",
            description = "Retorna uma lista de pessoas registradas com o nome fornecido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa retornada com sucesso",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Person data",
                                    value = """
                                    {
                                        "name": "Jeff Bezos",
                                        "birthDay": "25/08/1981",
                                        "addresses": [
                                            {
                                                "id": 3,
                                                "street": "Rua 10",
                                                "number": 475,
                                                "zipCode": "0555-656",
                                                "city": "Cidade das esmeraldas",
                                                "isMain": false
                                            },
                                            {
                                                "id": 4,
                                                "street": "Rua 9",
                                                "number": 478,
                                                "zipCode": "0555-434",
                                                "city": "Hong Kong",
                                                "isMain": true
                                            }
                                        ]
                                    }
                                            """)
                    })}),

            @ApiResponse(responseCode = "404", description = "Pesquisa sem retorno de dados",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "ObjectNotFoundException",
                                value = """
                                        {
                                          "status": "NOT_FOUND",
                                          "message": "No such data"
                                        }
                                    """)
                    })})
    })
    @GetMapping(value = "/name")
    public ResponseEntity<List<PersonDataReturnDTO>> findPersonByName(@RequestParam("name") String name) {
        List<PersonDataReturnDTO> list = personService.findByName(name).stream().map(PersonDataReturnDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Atualiazar endereço para principal",
            description = "Atualiza o endereço da pessoa com id fornecido para o principal"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso")})
    @ApiResponse(responseCode = "404", description = "Pessoa com id informado ou endereço com id informado não existe",
            content = {@Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "ObjectNotFoundException",
                            value = """
                                    {
                                      "status": "NOT_FOUND",
                                      "message": "No such data"
                                    }
                                """)
            })})
    @PutMapping(value = "/{personId}/ads/{addressId}/setMain")
    public ResponseEntity<Void> setMainAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        personService.setMainAddress(personId, addressId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Inserir um novo endereço para a pessoa com Id fornecido",
            description = "Insere novo endereço para pessoa no banco"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inserção realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na inserção",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "DataIntegrityViolationException",
                                    value = """
                                            {
                                              "status": "BAD_REQUEST",
                                              "message": "Error message"
                                            }
                                            """)
                    })})
    })
    @PostMapping(value = "/{personId}/ads/add")
    public ResponseEntity<Void> insertPersonAddress(@PathVariable Long personId, @RequestBody @Valid AddressDTO addressDTO) {
            personService.insertAddress(personId, addressDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Pesquisa de endereços da pessoa",
            description = "Retorna em lista os endereços da pessoa com ID fornecido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa retornada com sucesso",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Address List", value =
                                        """
                                        [
                                          {
                                            "street": "Rua 12",
                                            "number": 484,
                                            "zipCode": "0123-456",
                                            "city": "Cidade grande",
                                            "isMain": false
                                          },
                                          {
                                            "street": "Rua 11",
                                            "number": 456,
                                            "zipCode": "0545-255",
                                            "city": "Gottan City",
                                            "isMain": true
                                          }
                                        ]
                                       """)
                    })}),
            @ApiResponse(responseCode = "404", description = "Pesquisa sem retorno de dados",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "ObjectNotFoundException",
                                    value =
                                        """
                                            {
                                              "status": "NOT_FOUND",
                                              "message": "No such data"
                                            }
                                        """)
                    })})
    })
    @GetMapping(value = "/{personId}/ads")
    public ResponseEntity<List<AddressDTO>> findPersonAddresses(@PathVariable Long personId) {
        List<Address> ads = addressService.getPersonAddressesById(personId);
        List<AddressDTO> list = ads.stream().map(AddressDTO::new).toList();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Pesquisa de endereço principal da pessoa",
            description = "Retorna o endereço da pessoa com ID fornecido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa retornada com sucesso",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Address List", value =
                                    """
                                                {
                                                    "street": "Rua 12",
                                                    "number": 484,
                                                    "zipCode": "0123-456",
                                                    "city": "Cidade grande",
                                                    "isMain": true
                                                }
                                            """)
                    })}),
            @ApiResponse(responseCode = "404", description = "Pesquisa sem retorno de dados",
                    content = {@Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "ObjectNotFoundException",
                                    value = """
                                            {
                                              "status": "NOT_FOUND",
                                              "message": "No such data"
                                            }
                                            """
                            )
                    })})
    })
    @GetMapping(value = "/{personId}/address/main")
    public ResponseEntity<AddressDTO> findMainAddress(@PathVariable Long personId) {
        Address address = addressService.getMainAddress(personId);
        return ResponseEntity.ok().body(new AddressDTO(address));
    }

    @PutMapping(value = "/{personId}/ads/{addressId}/edit")
    public ResponseEntity<Void> editAddress(@PathVariable Long personId, @PathVariable Long addressId, @RequestBody AddressEditDTO addressEditDTO) {
        personService.editAddress(personId, addressId, addressEditDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/{personId}/ads/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        personService.deleteAddress(personId, addressId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
