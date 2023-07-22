package com.magdy.challlenge.fivvy.models.dtos;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DisclaimerRequestDTO {

    @NotBlank(message = "The name cannot be blank.")
    @Size(min = 3, message = "The name must have at least 3 characters.")
    private String name;

    @NotBlank(message = "The text cannot be blank.")
    @Size(min = 3, message = "The text must have at least 3 characters.")
    private String text;
    private double version;
}