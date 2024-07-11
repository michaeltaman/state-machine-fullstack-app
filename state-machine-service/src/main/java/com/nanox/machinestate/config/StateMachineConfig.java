package com.nanox.machinestate.config;

import com.nanox.machinestate.model.Flow;
import com.nanox.machinestate.model.State;
import com.nanox.machinestate.service.FlowService;
import com.nanox.machinestate.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.EnableStateMachine;

import java.util.List;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Autowired
    @Lazy
    private FlowService flowService;

    @Autowired
    @Lazy
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

            for (State state : flowStates) {
                String stateWithFlowContext = state.getName() + "-" + flow.getName();
                states.withStates()
                        .state(stateWithFlowContext, context -> {
                            context.getExtendedState().getVariables().put("currentFlow", flow);
                            context.getExtendedState().getVariables().put("initialState", stateWithFlowContext);
                        });
            }
        }
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.withExternal()
                .source("INITIAL_STATE").target("END_STATE").event("EVENT_NAME"); // Example transition
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }

    @Bean
    public StateMachine<String, String> stateMachine() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial("INITIAL_STATE")
                .state("STATE1")
                .end("END_STATE");

        builder.configureTransitions()
                .withExternal()
                .source("INITIAL_STATE").target("STATE1").event("E1")
                .and()
                .withExternal()
                .source("STATE1").target("END_STATE").event("E2");

        return builder.build();
    }
}
