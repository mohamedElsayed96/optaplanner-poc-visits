package com.gizsystems.optaplannerpoc.services.change;

import com.gizsystems.optaplannerpoc.model.EmployeeShift;
import com.gizsystems.optaplannerpoc.model.Task;
import org.optaplanner.core.api.solver.change.ProblemChange;
import org.optaplanner.core.api.solver.change.ProblemChangeDirector;

public class AddTaskProblemChange implements ProblemChange<EmployeeShift> {

    private final Task task;

    public AddTaskProblemChange(Task task) {
        this.task = task;
    }

    @Override
    public void doChange(EmployeeShift workingCallCenter, ProblemChangeDirector problemChangeDirector) {
        problemChangeDirector.addEntity(task, workingCallCenter.getTasks()::add);
    }
}
