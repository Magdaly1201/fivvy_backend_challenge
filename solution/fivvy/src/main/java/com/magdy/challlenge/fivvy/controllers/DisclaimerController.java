package com.magdy.challlenge.fivvy.controllers;

import com.magdy.challlenge.fivvy.models.dtos.DisclaimerRequestDTO;
import com.magdy.challlenge.fivvy.services.DisclaimerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/disclaimer")
@Slf4j
public class DisclaimerController {

    private final DisclaimerService disclaimerService;

    public DisclaimerController(DisclaimerService disclaimerService) {
        this.disclaimerService = disclaimerService;
    }
    @PostMapping
    public ResponseEntity<?> createDisclaimer(@Valid @RequestBody DisclaimerRequestDTO disclaimerDTO) {
        log.info("Received request to create a new disclaimer: {}", disclaimerDTO);
        return new ResponseEntity<>(disclaimerService.create(disclaimerDTO), HttpStatus.CREATED);
    }
}