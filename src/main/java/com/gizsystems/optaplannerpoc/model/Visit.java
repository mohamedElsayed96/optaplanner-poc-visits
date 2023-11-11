package com.gizsystems.optaplannerpoc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visit {
    private UUID id;
    private String customerName;
    private String service;
    private Duration estimateDuration;
    private Date submitDate;
    private boolean isPwd;

    public Visit(UUID id, String customerName, Skill skill ,boolean isPwd) {
        this.id = id;
        this.customerName = customerName;
        this.service = skill.name();
        this.estimateDuration = skill.getEstimateDuration();
        this.isPwd = isPwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(id, visit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
