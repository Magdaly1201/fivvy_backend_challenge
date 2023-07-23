package com.magdy.challlenge.fivvy.models.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AcceptanceResponseDTO {
    private String id;
    private String userId;
    private String disclaimerId;
    private LocalDateTime createAt;

}