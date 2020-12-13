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
@RequestMapping("/api/ncp/parameters")
@CrossOrigin(origins = "http://localhost:4200")
public class ParameterController {

    private final ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @PostMapping
    public ResponseEntity<Parameter> createParameter(@RequestBody Parameter entity) {
        Parameter createdDataSet = parameterService.create(entity);
        return new ResponseEntity<>(createdDataSet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parameter> editParameter(@RequestBody Parameter entity) {
        Optional<Parameter> updatedDataSet = parameterService.update(entity);
        return updatedDataSet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteParameter(@PathVariable int id) {
        int usages = parameterService.delete(id);
        if(usages == 0){
            return new ResponseEntity<>(0,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(usages,HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parameter> getParameter(@PathVariable int id) {
        Optional<Parameter> entity =  parameterService.findById(id);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/usages")
    public ResponseEntity<Integer> getNumberOfUsages(@PathVariable int id) {
        return new ResponseEntity<>(parameterService.getNumberOfUsages(id),HttpStatus.OK);
    }
}
