package com.gizsystems.optaplannerpoc.services;

import com.gizsystems.optaplannerpoc.model.Agent;
import com.gizsystems.optaplannerpoc.model.EmployeeShift;
import com.gizsystems.optaplannerpoc.model.Task;
import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


public class EndTimeUpdatingListener implements VariableListener<EmployeeShift, Task> {
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
        if (task.getEstimateDuration() == null || task.getStartTime() == null) {
            scoreDirector.beforeVariableChanged(task, "endTime");
            task.setEndTime(null);
            scoreDirector.afterVariableChanged(task, "endTime");
            return;
        }
        var endDate = task.getStartTime().plus(task.getEstimateDuration());
        scoreDirector.beforeVariableChanged(task, "endTime");
        task.setEndTime(endDate);
        scoreDirector.afterVariableChanged(task, "endTime");

    }
}
