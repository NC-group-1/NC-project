package com.nc.project.controller;

import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.dto.Page;
import com.nc.project.model.Parameter;
import com.nc.project.service.dataSet.DataSetService;
import com.nc.project.service.parameter.ParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/datasets")
@CrossOrigin(origins = "http://localhost:4200")
public class DataSetController {

    private final DataSetService dataSetService;
    private final ParameterService parameterService;

    public DataSetController(DataSetService dataSetService, ParameterService parameterService) {
        this.dataSetService = dataSetService;
        this.parameterService = parameterService;
    }

    @GetMapping
    public ResponseEntity<Page<DataSetGeneralInfoDto>> getDataSetPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter,
            @RequestParam(name = "orderBy",defaultValue = "name") String orderBy,
            @RequestParam(name = "order", defaultValue = "ASC") String order) {
        Page<DataSetGeneralInfoDto> resultPage = dataSetService.getAllByPage(page, size, filter, orderBy, order);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @GetMapping("/{id}/parameters")
    public ResponseEntity<List<Parameter>> getParametersByDataSetId (@PathVariable int id) {
        List<Parameter> resultList = parameterService.getParametersByDataSetId(id);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DataSetGeneralInfoDto> createDataSet(@RequestBody DataSetGeneralInfoDto entity) {
        DataSetGeneralInfoDto createdDataSet = dataSetService.create(entity);
        return new ResponseEntity<>(createdDataSet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataSetGeneralInfoDto> editDataSet(@RequestBody DataSetGeneralInfoDto entity) {
        DataSetGeneralInfoDto updatedDataSet = dataSetService.update(entity);
        return new ResponseEntity<>(updatedDataSet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDataSet(@PathVariable int id) {
        dataSetService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataSetGeneralInfoDto> getDataSet(@PathVariable int id) {
        Optional<DataSetGeneralInfoDto> entity = dataSetService.findById(id);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
