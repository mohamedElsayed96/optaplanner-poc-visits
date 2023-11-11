package com.gizsystems.optaplannerpoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class VisitorEntity {
    @Id
    private long id;
    private String name;
    private boolean pwd;
}
