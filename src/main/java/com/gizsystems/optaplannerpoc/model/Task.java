package com.gizsystems.optaplannerpoc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizsystems.optaplannerpoc.services.EndTimeUpdatingListener;
import com.gizsystems.optaplannerpoc.services.EstimatedDurationUpdatingListener;
import com.gizsystems.optaplannerpoc.services.NumberUpdatingListener;
import com.gizsystems.optaplannerpoc.services.StartTimeUpdatingListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@PlanningEntity
@Getter
@Setter
@NoArgsConstructor
public class Task extends PreviousTaskOrAgent {

    private UUID id;

    @ShadowVariable(sourceVariableName = "number", variableListenerClass = StartTimeUpdatingListener.class)
    private LocalTime startTime;

    @ShadowVariable(sourceVariableName = "startTime", variableListenerClass = EndTimeUpdatingListener.class)
    @ShadowVariable(sourceVariableName = "estimateDuration", variableListenerClass = EndTimeUpdatingListener.class)
    private LocalTime endTime;

    @ShadowVariable(sourceVariableName = "visit", variableListenerClass = EstimatedDurationUpdatingListener.class)
    private Duration estimateDuration;

    @ShadowVariable(sourceVariableName = "visit", variableListenerClass = NumberUpdatingListener.class)
    @ShadowVariable(sourceVariableName = "previousTaskOrAgent", variableListenerClass = NumberUpdatingListener.class)
    private Integer number;

    @AnchorShadowVariable(sourceVariableName = "previousTaskOrAgent")
    private Agent agent;

    @PlanningVariable

    private Visit visit;

    @JsonIgnore
    @PlanningVariable(graphType = PlanningVariableGraphType.CHAINED)
    private PreviousTaskOrAgent previousTaskOrAgent;


    public Task(UUID id) {
        super(id);
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public Integer getPreviousNumber() {
        return number;
    }

    @Override
    public LocalTime getPreviousEndTime() {
        return endTime;
    }

}
