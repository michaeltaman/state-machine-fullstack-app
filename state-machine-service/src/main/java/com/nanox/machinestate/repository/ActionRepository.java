package com.nanox.machinestate.repository;

import com.nanox.machinestate.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    // Define custom query methods here
}