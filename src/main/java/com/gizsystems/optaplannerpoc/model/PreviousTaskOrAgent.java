package com.gizsystems.optaplannerpoc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

import java.time.LocalTime;
import java.util.UUID;

@PlanningEntity
@Data
@NoArgsConstructor
public abstract class PreviousTaskOrAgent {

    @PlanningId
    private UUID id;
    @JsonIgnore
    @InverseRelationShadowVariable(sourceVariableName = "previousTaskOrAgent")
    protected Task nextTask;

    @JsonIgnore
    public abstract Integer getPreviousNumber();
    @JsonIgnore
    public abstract LocalTime getPreviousEndTime();

    public PreviousTaskOrAgent(UUID id) {
        this.id = id;
    }
}
