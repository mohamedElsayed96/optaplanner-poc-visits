package com.gizsystems.optaplannerpoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Duration;

@Data
@Entity
public class ServiceEntity {
    @Id
    private long id;
    private String name;
    private Duration estimatedDuration;
}
