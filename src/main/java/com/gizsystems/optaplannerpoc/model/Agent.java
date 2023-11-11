package com.gizsystems.optaplannerpoc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Agent extends PreviousTaskOrAgent {
    private UUID id;
    private String name;
    private Set<String> supportedServices;
    private boolean supportPwd;

    public Agent(UUID id, String name, Set<String> supportedServices, boolean supportPwd) {
        super(id);
        this.id = id;
        this.name = name;
        this.supportedServices = supportedServices;
        this.supportPwd = supportPwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Agent agent = (Agent) o;
        return id == agent.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public Integer getPreviousNumber() {
        return 0;
    }

    @Override
    public LocalTime getPreviousEndTime() {
        return null;
    }
}
