package com.magdy.challlenge.fivvy.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "disclaimers")
@Data
public class Disclaimer {

    @Id
    private String id;
    private String name;
    private String text;
    private double version;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}