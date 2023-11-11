package com.gizsystems.optaplannerpoc.services;

import com.gizsystems.optaplannerpoc.model.Agent;
import com.gizsystems.optaplannerpoc.model.EmployeeShift;
import com.gizsystems.optaplannerpoc.model.Task;
import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;

import java.time.LocalTime;


public class EstimatedDurationUpdatingListener implements VariableListener<EmployeeShift, Task> {
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
        if(task.getVisit() == null){
            scoreDirector.beforeVariableChanged(task, "estimateDuration");
            task.setEstimateDuration(null);
            scoreDirector.afterVariableChanged(task, "estimateDuration");
            return;
        }
        scoreDirector.beforeVariableChanged(task, "estimateDuration");
        task.setEstimateDuration(task.getVisit().getEstimateDuration());
        scoreDirector.afterVariableChanged(task, "estimateDuration");
    }
}
