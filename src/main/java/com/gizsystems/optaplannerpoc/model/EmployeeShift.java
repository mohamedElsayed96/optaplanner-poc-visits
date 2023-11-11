package com.gizsystems.optaplannerpoc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

@PlanningSolution
@Data
@NoArgsConstructor
public class EmployeeShift {

    @ProblemFactCollectionProperty
    @ValueRangeProvider
    @JsonIgnore
    private List<Agent> agents;
    @ProblemFactCollectionProperty
    @ValueRangeProvider
    @JsonIgnore
    private List<Visit> visits;

    @PlanningEntityCollectionProperty
    private List<Task> tasks;

    @PlanningScore
    private HardSoftScore score;

    private SolverStatus solverStatus;
    public EmployeeShift(List<Agent> agents, List<Visit> visits, List<Task> tasks) {
        this.agents = agents;
        this.tasks = tasks;
        this.visits = visits;
    }
}
