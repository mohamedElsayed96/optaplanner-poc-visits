package com.gizsystems.optaplannerpoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class VisitEntity {
    @Id
    private long id;
    @OneToOne
    private ServiceEntity service;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    private EmployeeEntity employee;
    @ManyToOne
    private VisitorEntity visitor;
    @OneToOne
    private VisitorEntity previousVisit;
}
