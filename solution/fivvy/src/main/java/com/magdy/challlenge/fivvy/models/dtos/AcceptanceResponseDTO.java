package com.magdy.challlenge.fivvy.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AcceptanceResponseDTO {

    private String userId;
    private String disclaimerId;
    private LocalDateTime createAt;

}