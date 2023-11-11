package com.gizsystems.optaplannerpoc.services.change;

import com.gizsystems.optaplannerpoc.model.EmployeeShift;
import com.gizsystems.optaplannerpoc.model.PreviousTaskOrAgent;
import com.gizsystems.optaplannerpoc.model.Task;
import org.optaplanner.core.api.solver.change.ProblemChange;
import org.optaplanner.core.api.solver.change.ProblemChangeDirector;

import java.util.Optional;
import java.util.UUID;

public class RemoveTaskProblemChange implements ProblemChange<EmployeeShift> {

    private final UUID callId;

    public RemoveTaskProblemChange(UUID callId) {
        this.callId = callId;
    }

    @Override
    public void doChange(EmployeeShift workingCallCenter, ProblemChangeDirector problemChangeDirector) {
        var call = new Task(callId);
        Optional<Task> workingCallOptional = problemChangeDirector.lookUpWorkingObject(call);
        workingCallOptional.ifPresent(workingCall -> removeCall(workingCall, workingCallCenter, problemChangeDirector));
    }

    private void removeCall(Task call, EmployeeShift workingCallCenter, ProblemChangeDirector problemChangeDirector) {
        PreviousTaskOrAgent previousCallOrAgent = call.getPreviousTaskOrAgent();

        var nextCall = call.getNextTask();
        if (nextCall != null) {
            problemChangeDirector.changeVariable(nextCall, "previousCallOrAgent",
                    workingNextCall -> workingNextCall.setPreviousTaskOrAgent(previousCallOrAgent));
        }

        problemChangeDirector.removeEntity(call, workingCallCenter.getTasks()::remove);
    }
}
