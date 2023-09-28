package com.attornatus.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressEditDTO {

    @Column(length = 45, nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer number;

    @Column(length = 9, nullable = false)
    @JsonFormat(pattern = "nnnnn-nnn")
    private String zipCode;

    @Column(length = 35, nullable = false)
    private String city;
}
