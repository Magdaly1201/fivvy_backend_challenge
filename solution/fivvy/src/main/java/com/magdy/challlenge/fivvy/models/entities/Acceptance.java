package com.magdy.challlenge.fivvy.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "acceptances")
@Getter
@Setter
public class Acceptance {
    @Id
    private String id;
    private String userId;

    @DBRef
    private Disclaimer disclaimerId;
    private LocalDateTime createAt;
}