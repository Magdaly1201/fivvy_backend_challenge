package com.magdy.challlenge.fivvy.models.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AcceptanceRequestDTO {

    @NotBlank(message = "The userId cannot be blank.")
    private String userId;

}