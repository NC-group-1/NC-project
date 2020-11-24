package com.nc.project.controller;

import com.nc.project.model.Action;
import com.nc.project.service.library.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Action>> getActionPage(@RequestParam(name = "page") int page,
                                                      @RequestParam(name = "size") int size) {
        List<Action> actions = actionService.getAllActionsByPage(page, size);
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Action> getAction(@PathVariable int id) {
        Action action = actionService.getActionById(id);
        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Action> getActionByKey(@RequestParam(name="key") String key) {
        Action action = actionService.getActionByKey(key);
        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Action> editAction(@RequestBody Action action) {
        Action updatedAction = actionService.editAction(action);
        return new ResponseEntity<>(updatedAction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAction(@RequestBody int id) {
        actionService.deleteAction(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
