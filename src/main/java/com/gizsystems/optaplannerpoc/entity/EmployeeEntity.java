package com.gizsystems.optaplannerpoc.entity;

import com.gizsystems.optaplannerpoc.model.Service;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class EmployeeEntity {
    @Id
    private long id;
    private String name;
    @OneToOne
    private ServiceEntity supportedServices;

    private boolean supportPwd;
}
