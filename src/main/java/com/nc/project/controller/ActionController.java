package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.service.action.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/actions")
@CrossOrigin(origins = "http://localhost:4200")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping
    public ResponseEntity<Action> createAction(@RequestBody Action action) {
        Action createdAction = actionService.createAction(action);
        return new ResponseEntity<>(createdAction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Action>> getActionPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Action> resultPage = actionService.getAllActionsByPage(page, size);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Action>> getActionPageWithFilter(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter") String filter,
            @RequestParam(name = "filterTable",defaultValue = "name") String filterTable,
            @RequestParam(name = "orderBy",defaultValue = "name") String orderBy,
            @RequestParam(name = "order", defaultValue = "ASC") String order) {
        Page<Action> resultPage = actionService.getAllActionsByPage(page, size, filter, filterTable, orderBy, order);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @GetMapping("/compounds/{targetId}")
    public ResponseEntity<Page<Action>> getActionPageWithoutTarget(@PathVariable int targetId,
                                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Action> resultPage = actionService.getAllActionsByPage(page, size, targetId);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Action> getAction(@PathVariable int id) {
        Optional<Action> action = actionService.getActionById(id);
        return action.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Action>> getActionByName(@RequestParam(name="name") String name) {
        List<Action> actions = actionService.getActionByName(name);
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Action> editAction(@RequestBody Action action) {
        Action updatedAction = actionService.editAction(action);
        return new ResponseEntity<>(updatedAction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAction(@PathVariable int id) {
        actionService.deleteAction(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getActionTypes() {
        return new ResponseEntity<>(actionService.getActionTypes(), HttpStatus.OK);
    }
}
