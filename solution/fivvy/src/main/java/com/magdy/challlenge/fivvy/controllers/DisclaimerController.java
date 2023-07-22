package com.magdy.challlenge.fivvy.controllers;

import com.magdy.challlenge.fivvy.exceptions.DisclaimerNotFoundException;
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
    @GetMapping("/search")
    public ResponseEntity<?> getDisclaimerByFilter(@RequestParam(required = false, defaultValue = "") String searchText) {
        log.info("Received request to get list by filter");
        return new ResponseEntity<>(disclaimerService.findByFilter(searchText), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getById(@RequestParam(required = true) String id) throws DisclaimerNotFoundException {
        log.info("Received request to get disclaimer by id");
        return new ResponseEntity<>(disclaimerService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(@RequestParam(required = true) String id) {
        log.info("Received request to delete disclaimer by id");
        disclaimerService.deleteById(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody DisclaimerRequestDTO disclaimerDTO) throws DisclaimerNotFoundException {
        log.info("Received request to get update by id");
        return new ResponseEntity<>(disclaimerService.update(id, disclaimerDTO), HttpStatus.OK);
    }
}