package com.nanox.machinestate.action;

import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class SampleAction implements StateMachineAction {

    @Override
    public void performAction(StateContext<String, String> context) {
        // Perform your custom action here
        System.out.println("Executing action for event: " + context.getEvent());
    }
}
