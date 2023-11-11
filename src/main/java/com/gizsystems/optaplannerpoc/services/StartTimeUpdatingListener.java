package com.gizsystems.optaplannerpoc.services;

import com.gizsystems.optaplannerpoc.model.Agent;
import com.gizsystems.optaplannerpoc.model.EmployeeShift;
import com.gizsystems.optaplannerpoc.model.Task;
import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;

import java.time.LocalTime;


public class StartTimeUpdatingListener implements VariableListener<EmployeeShift, Task> {
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
        var previous = task.getPreviousTaskOrAgent();
        LocalTime previousEndTime;
        if (task.getNumber() == null) {
            previousEndTime = null;
        } else if (task.getNumber() <= 1) {
            previousEndTime = LocalTime.of(8, 0, 0);

        } else {
            previousEndTime = previous.getPreviousEndTime();
        }

        var shadowTask = task;
        while (shadowTask != null) {
            scoreDirector.beforeVariableChanged(shadowTask, "startTime");
            shadowTask.setStartTime(previousEndTime);
            scoreDirector.afterVariableChanged(shadowTask, "startTime");
            previousEndTime = shadowTask.getEndTime();
            shadowTask = shadowTask.getNextTask();
        }
    }
}
