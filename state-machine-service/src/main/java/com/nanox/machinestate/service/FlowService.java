package com.nanox.machinestate.service;

import com.nanox.machinestate.exception.DuplicateResourceException;
import com.nanox.machinestate.exception.ResourceNotFoundException;
import com.nanox.machinestate.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.repository.FlowRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@Service
public class FlowService {

    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private StateRepository stateRepository;

    @Transactional(readOnly = true)
    public List<Flow> getAllFlows() {
        return flowRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Flow> findByName(String name) {
        return flowRepository.findByName(name);
    }

    @Transactional
    public Flow saveFlow(Flow flow) {
        try {
            return flowRepository.save(flow);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("A flow with the name '" + flow.getName() + "' already exists.");
        }
    }

    @Transactional
    public Flow addFlow(Flow flow) {
        try {
            return flowRepository.save(flow);
        } catch (DataIntegrityViolationException e) {
            // Optionally, convert to a more specific or user-friendly exception
            throw new DuplicateResourceException("A flow with the same name already exists.");
        }
    }

    @Transactional(readOnly = true)
    public Flow getFlowById(long id) {
        return flowRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Flow not found with id: " + id));
    }

    /*@Transactional
    public void deleteFlow(long id) {
        // Check if the flow exists
        Flow flow = flowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flow not found with id: " + id));

        // Proceed with deletion
        flowRepository.deleteById(id);
    }*/

    @Transactional
    public void deleteFlow(long id) {
        Flow flow = flowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flow not found with id: " + id));

        // Delete all states associated with this flow
        stateRepository.deleteByFlow(flow);

        // Delete the flow
        flowRepository.deleteById(id);
    }

    /*@Transactional
    public void deleteFlowByName(String name) {
        Flow flow = flowRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Flow not found with name: " + name));
        flowRepository.delete(flow);
    }*/

    @Transactional
    public void deleteFlowByName(String name) {
        Flow flow = flowRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Flow not found with name: " + name));

        // Delete all states associated with this flow
        stateRepository.deleteByFlow(flow);

        // Delete the flow
        flowRepository.delete(flow);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleCustomDuplicateResourceException(DuplicateResourceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
