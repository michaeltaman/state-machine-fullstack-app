package com.nanox.machinestate.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.nanox.machinestate.model.Action;
import com.nanox.machinestate.repository.ActionRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ActionServiceTest {

    @Mock
    private ActionRepository actionRepository;

    @InjectMocks
    private ActionService actionService;

    private Action action;

    @BeforeEach
    void setUp() {
        action = new Action();
        // Initialize your action object here
    }

    @Test
    void testAddAction() {
        when(actionRepository.save(any(Action.class))).thenReturn(action);
        Action created = actionService.addAction(new Action());
        assertNotNull(created);
        // Additional assertions as necessary
    }

    @Test
    void testGetActionById() {
        when(actionRepository.findById(anyLong())).thenReturn(Optional.of(action));
        Action found = actionService.getActionById(1L);
        assertNotNull(found);
        // Additional assertions as necessary
    }

    @Test
    void testDeleteAction() {
        long actionId = 1L; // The ID of the action to delete
        // Mock the repository to simulate the action's existence
        when(actionRepository.findById(actionId)).thenReturn(Optional.of(new Action()));
        doNothing().when(actionRepository).deleteById(actionId);

        // Attempt to delete the action, expecting no exceptions
        assertDoesNotThrow(() -> actionService.deleteAction(actionId));

        // Verify the delete operation was called on the repository
        verify(actionRepository).deleteById(actionId);
    }

    @Test
    void testUpdateAction() {
        // Arrange
        Long actionId = 1L;
        Action existingAction = new Action(); // Assuming Action is your entity class
        existingAction.setId(actionId);
        existingAction.setName("Original Name");
        // Assuming you have a repository or similar to interact with the database
        when(actionRepository.findById(actionId)).thenReturn(Optional.of(existingAction));

        Action updatedDetails = new Action();
        updatedDetails.setName("Updated Name");
        when(actionRepository.save(any(Action.class))).thenReturn(updatedDetails);

        // Act
        Action result = actionService.updateAction(actionId, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(actionRepository).save(any(Action.class)); // Verify save was called
    }
}