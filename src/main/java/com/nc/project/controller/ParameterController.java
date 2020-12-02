package com.nc.project.controller;

import com.nc.project.model.Parameter;
import com.nc.project.service.parameter.ParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/parameters")
@CrossOrigin(origins = "http://localhost:4200")
public class ParameterController {

    private final ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @PostMapping
    public ResponseEntity<Parameter> createDataSet(@RequestBody Parameter entity) {
        Parameter createdDataSet = parameterService.create(entity);
        return new ResponseEntity<>(createdDataSet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parameter> editDataSet(@RequestBody Parameter entity) {
        Parameter updatedDataSet = parameterService.update(entity);
        return new ResponseEntity<>(updatedDataSet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDataSet(@PathVariable int id) {
        parameterService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parameter> getDataSet(@PathVariable int id) {
        Optional<Parameter> entity =  parameterService.findById(id);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
