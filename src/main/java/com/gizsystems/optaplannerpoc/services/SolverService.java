package com.gizsystems.optaplannerpoc.services;


import com.gizsystems.optaplannerpoc.model.EmployeeShift;
import com.gizsystems.optaplannerpoc.model.Task;
import com.gizsystems.optaplannerpoc.services.change.AddTaskProblemChange;
import com.gizsystems.optaplannerpoc.services.change.RemoveTaskProblemChange;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.optaplanner.core.api.solver.change.ProblemChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SolverService {

    private final SolverManager<EmployeeShift, Long> solverManager;
    public static final long SINGLETON_ID = 1L;
    private AtomicReference<EmployeeShift> bestSolution = new AtomicReference<>();

    private final BlockingQueue<WaitingProblemChange> waitingProblemChanges = new LinkedBlockingQueue<>();

    @Autowired
    public SolverService(SolverManager<EmployeeShift, Long> solverManager, DataGenerator dataGenerator) {
        this.solverManager = solverManager;
        bestSolution.set(dataGenerator.generateEmployeeShift());
    }

//    private void pinCallAssignedToAgents(List<Call> calls) {
//        calls.stream()
//                .filter(call -> !call.isPinned()
//                        && call.getPreviousCallOrAgent() != null
//                        && call.getPreviousCallOrAgent() instanceof Agent)
//                .map(PinCallProblemChange::new)
//                .forEach(problemChange -> solverManager.addProblemChange(SINGLETON_ID, problemChange));
//    }

    public void startSolving() {
        solverManager.solveAndListen(SINGLETON_ID,
                id -> bestSolution.get(),this::setBestSolution,
                this::setBestSolution, null);
    }

    public EmployeeShift getBestSolution(){
        var solution =  bestSolution.get();
        solution.setSolverStatus(getStatus());
        return solution;
    }
    public void setBestSolution(EmployeeShift employeeShift){
        bestSolution.set(employeeShift);
    }
    public void stopSolving() {
        solverManager.terminateEarly(SINGLETON_ID);
    }

    public boolean isSolving() {
        return solverManager.getSolverStatus(SINGLETON_ID) != SolverStatus.NOT_SOLVING;
    }
    public SolverStatus getStatus() {
        return solverManager.getSolverStatus(SINGLETON_ID);
    }

    public CompletableFuture<Void> addVisit(Task call) {
        return registerProblemChange(new AddTaskProblemChange(call));
    }

    public CompletableFuture<Void> removeCall(UUID callId) {
        return registerProblemChange(new RemoveTaskProblemChange(callId));
    }

//    public CompletableFuture<Void> prolongCall(long callId) {
//        return registerProblemChange(new ProlongCallByMinuteProblemChange(callId));
//    }

    private CompletableFuture<Void> registerProblemChange(ProblemChange<EmployeeShift> problemChange) {
        if (isSolving()) {
            return solverManager.addProblemChange(SINGLETON_ID, problemChange);
        } else {
            /*
             * Expose a temporary CompletableFuture that will get completed once the solver is started again
             * and processes the change.
             */
            CompletableFuture<Void> completion = new CompletableFuture<>();
            waitingProblemChanges.add(new WaitingProblemChange(completion, problemChange));
            return completion;
        }
    }


    private record WaitingProblemChange(CompletableFuture<Void> completion,
                                        ProblemChange<EmployeeShift> problemChange) {

    }
}
