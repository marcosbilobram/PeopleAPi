package com.attornatus.project.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "tb_address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String street; //logradouro

    @Column(nullable = false)
    private Integer number; //número

    @Column(length = 9, nullable = false)
    @JsonFormat(pattern = "nnnnn-nnn")
    private String zipCode; //CEP

    @Column(length = 20, nullable = false)
    private String city; //cidade

    private Boolean isMain = false; //atributo que definirá se o endereço é o principal ou não

    @ManyToOne
    @JoinColumn(
            name = "person_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Person person; //classe pessoa
}
