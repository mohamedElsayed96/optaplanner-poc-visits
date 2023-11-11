package com.gizsystems.optaplannerpoc.controllers;

import com.gizsystems.optaplannerpoc.model.EmployeeShift;
import com.gizsystems.optaplannerpoc.services.DataGenerator;
import com.gizsystems.optaplannerpoc.services.SolverService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.concurrent.atomic.AtomicReference;

@RequestMapping("/employee-shift")
@RestController
@RequiredArgsConstructor
public class EmployeeShiftController {

//    private AtomicReference<Throwable> solvingError = new AtomicReference<>();


    private final SolverService solverService;



    @Autowired
    EmployeeShiftController(DataGenerator dataGenerator, SolverService solverService) {
        this.solverService = solverService;
    }

    @GetMapping()
    public EmployeeShift get() {

        return solverService.getBestSolution();
    }


    @PostMapping("solve")
    public void solve() {
        solverService.startSolving();
    }


    @PostMapping("stop")
    public void stop() {
        solverService.stopSolving();
    }
}
