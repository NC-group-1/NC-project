package com.nc.project.controller;

import com.nc.project.model.ParameterKey;
import com.nc.project.service.parameterKey.ParameterKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/keys")
@CrossOrigin(origins = "http://localhost:4200")
public class ParameterKeyController {
    private final ParameterKeyService parameterKeyService;

    public ParameterKeyController(ParameterKeyService parameterKeyService) {
        this.parameterKeyService = parameterKeyService;
    }

    @GetMapping
    public ResponseEntity<List<ParameterKey>> getKeysByName(@RequestParam(name="name") String name) {
        List<ParameterKey> keys = parameterKeyService.getKeysByName(name);
        return new ResponseEntity<>(keys,HttpStatus.OK);
    }

}
