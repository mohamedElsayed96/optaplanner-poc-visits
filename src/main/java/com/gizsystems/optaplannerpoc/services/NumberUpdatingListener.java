package com.gizsystems.optaplannerpoc.services;

import com.gizsystems.optaplannerpoc.model.EmployeeShift;
import com.gizsystems.optaplannerpoc.model.Task;
import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;


public class NumberUpdatingListener implements VariableListener<EmployeeShift, Task> {
    @Override
    public void beforeVariableChanged(ScoreDirector<EmployeeShift> scoreDirector, Task task) {

    }

    @Override
    public void afterVariableChanged(ScoreDirector<EmployeeShift> scoreDirector, Task task) {
        updateResponseTime(scoreDirector, task);
    }

    @Override
    public void beforeEntityAdded(ScoreDirector<EmployeeShift> scoreDirector, Task task) {

    }

    @Override
    public void afterEntityAdded(ScoreDirector<EmployeeShift> scoreDirector, Task task) {
        updateResponseTime(scoreDirector, task);
    }

    @Override
    public void beforeEntityRemoved(ScoreDirector<EmployeeShift> scoreDirector, Task task) {

    }

    @Override
    public void afterEntityRemoved(ScoreDirector<EmployeeShift> scoreDirector, Task task) {

    }

    protected void updateResponseTime(ScoreDirector<EmployeeShift> scoreDirector, Task task) {
        Integer nextNumber;
        var previous = task.getPreviousTaskOrAgent();

        if (task.getVisit() == null || previous == null || previous.getPreviousNumber() == null) {
            nextNumber = null;
        } else {
            nextNumber = previous.getPreviousNumber() + 1;
        }
        var shadowTask = task;
        while (shadowTask != null) {
            scoreDirector.beforeVariableChanged(task, "number");
            task.setNumber(nextNumber);
            scoreDirector.afterVariableChanged(task, "number");
            if (nextNumber != null)
                nextNumber = nextNumber + 1;
            shadowTask = shadowTask.getNextTask();
        }
    }

}
