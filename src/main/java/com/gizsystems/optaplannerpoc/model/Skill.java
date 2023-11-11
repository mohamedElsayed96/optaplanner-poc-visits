package com.gizsystems.optaplannerpoc.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.time.Duration;

@Getter
public enum Skill {

    ENGLISH("EN", Duration.ofMinutes(15)),
    GERMAN("DE", Duration.ofMinutes(15)),
    SPANISH("ES", Duration.ofMinutes(15)),
    CAR_INSURANCE("Car insurance", Duration.ofMinutes(15)),
    LIFE_INSURANCE("Life insurance", Duration.ofMinutes(15)),
    PROPERTY_INSURANCE("Property insurance", Duration.ofMinutes(15));

    private final String name;
    private final Duration estimateDuration;

    Skill(String name, Duration estimateDuration) {
        this.name = name;
        this.estimateDuration = estimateDuration;
    }
}
