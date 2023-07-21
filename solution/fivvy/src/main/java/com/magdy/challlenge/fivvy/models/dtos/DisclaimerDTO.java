package com.magdy.challlenge.fivvy.models.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class DisclaimerDTO {
    //TODO: add validations the DTO
    @Id
    private String id;
    private String name;
    private String text;
    private double version;

}
