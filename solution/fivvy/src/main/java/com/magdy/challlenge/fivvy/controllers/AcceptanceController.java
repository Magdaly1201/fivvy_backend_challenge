package com.magdy.challlenge.fivvy.controllers;

import com.magdy.challlenge.fivvy.exceptions.DisclaimerNotFoundException;
import com.magdy.challlenge.fivvy.models.dtos.AcceptanceRequestDTO;
import com.magdy.challlenge.fivvy.services.AcceptanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/disclaimer")
@Slf4j
@AllArgsConstructor
public class AcceptanceController {

    private final AcceptanceService acceptanceService;

    @PostMapping("/{disclaimerId}/acceptance")
    public ResponseEntity<?> addAcceptanceByDisclaimerId(@PathVariable String disclaimerId, @Valid @RequestBody AcceptanceRequestDTO acceptanceRequestDTO) throws DisclaimerNotFoundException {
        log.info("Received request to add acceptance by disclaimer id {}",disclaimerId);
        return new ResponseEntity<>(acceptanceService.create(disclaimerId, acceptanceRequestDTO), HttpStatus.CREATED);
    }
    @PostMapping("/acceptance/{userId}")
    public ResponseEntity<?> addAcceptanceByDisclaimerId(@PathVariable String userId) throws DisclaimerNotFoundException {
        log.info("Received request to add acceptance by user id {}", userId);
        return new ResponseEntity<>(acceptanceService.getAllAcceptanceByUserId(userId), HttpStatus.OK);
    }
}
