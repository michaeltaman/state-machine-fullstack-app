package com.nanox.machinestate.config;

import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.model.State;
import com.nanox.machinestate.service.FlowService;
import com.nanox.machinestate.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.StateMachine;

import java.util.List;
import java.util.HashSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Autowired
    private FlowService flowService;

    @Autowired
    private StateService stateService;


    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states.withStates()
                .initial("INITIAL_STATE")
                .end("END_STATE");

        List<Flow> flows = flowService.getAllFlows();
        for (Flow flow : flows) {
            List<State> flowStates = stateService.getStatesByFlow(flow);
            if (flowStates.isEmpty()) continue;

            // Collect state names
            List<String> stateNames = flowStates.stream()
                    .map(State::getName)
                    .toList();

            // Configure states with initial and end state
            states.withStates()
                    .initial(stateNames.get(0))
                    .end(stateNames.get(stateNames.size() - 1))
                    .states(new HashSet<>(stateNames));
        }
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        boolean hasTransitions = false;
        List<Flow> flows = flowService.getAllFlows();
        for (Flow flow : flows) {
            List<State> flowStates = stateService.getStatesByFlow(flow);
            if (flowStates.isEmpty()) continue;

            // Add transitions based on state order
            for (int i = 0; i < flowStates.size() - 1; i++) {
                transitions.withExternal()
                        .source(flowStates.get(i).getName())
                        .target(flowStates.get(i + 1).getName())
                        .event(flow.getName() + "_EVENT" + (i + 1));
                hasTransitions = true;
            }
        }

        // Ensure there's at least one default transition to satisfy state machine requirements
        if (!hasTransitions) {
            transitions.withExternal()
                    .source("INITIAL_STATE")
                    .target("END_STATE")
                    .event("DEFAULT_EVENT");
        }
    }

    @Bean
    public StateMachine<String, String> buildStateMachine() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        configure(builder.configureStates());
        configure(builder.configureTransitions());

        return builder.build();
    }
}
