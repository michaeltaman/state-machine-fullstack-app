package com.nanox.machinestate.service;

import com.nanox.machinestate.model.Action;
import com.nanox.machinestate.repository.ActionRepository;
import com.nanox.machinestate.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class ActionService {

    private final ActionRepository actionRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Transactional(readOnly = true)
    public List<Action> getAllActions() {
        return actionRepository.findAll();
    }

    @Transactional
    public Action saveAction(Action action) {
        return actionRepository.save(action);
    }

    @Transactional
    public Action addAction(Action action) {
        return actionRepository.save(action);
    }

    @Transactional(readOnly = true)
    public Action getActionById(long id) {
        return actionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + id));
    }

    @Transactional
    public void deleteAction(long id) {
        // Check if the action exists
        Action action = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + id));

        // Proceed with deletion
        actionRepository.deleteById(id);
    }

    public Action updateAction(Long actionId, Action updatedDetails) {
        // Find the existing action by id
        Action existingAction = actionRepository.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id " + actionId));

        existingAction.setName(updatedDetails.getName());

        // Return the updated action
        return actionRepository.save(existingAction);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}