package com.nc.project.controller;

import com.nc.project.model.Compound;
import com.nc.project.service.compound.CompoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/compounds")
@CrossOrigin(origins = "http://localhost:4200")
public class CompoundController {
    private final CompoundService compoundService;

    public CompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @PostMapping
    public ResponseEntity<Compound> createAction(@RequestBody Compound compound) {
        Compound createdCompound = compoundService.createCompound(compound);
        return new ResponseEntity<>(createdCompound, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compound> editCompound(@RequestBody Compound compound) {
        Compound updatedCompound = compoundService.editCompound(compound);
        return new ResponseEntity<>(updatedCompound, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCompound(@RequestBody Compound compound) {
        compoundService.deleteCompound(compound);
        return new ResponseEntity(HttpStatus.OK);
    }


}
