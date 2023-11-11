package com.gizsystems.optaplannerpoc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    private UUID id;
    private String name;
    private Duration estimatedDuration;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
