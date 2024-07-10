package com.nanox.machinestate.controller;

import com.nanox.machinestate.model.Action;
import com.nanox.machinestate.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private final ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping
    public ResponseEntity<Action> createAction(@RequestBody Action action) {
        Action savedAction = actionService.saveAction(action);
        return ResponseEntity.ok(savedAction);
    }

    @GetMapping
    public ResponseEntity<List<Action>> getAllActions() {
        List<Action> actions = actionService.getAllActions();
        return ResponseEntity.ok(actions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        actionService.deleteAction(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Action> updateAction(@PathVariable Long id, @RequestBody Action actionDetails) {
        Action updatedAction = actionService.updateAction(id, actionDetails);
        return ResponseEntity.ok(updatedAction);
    }
}