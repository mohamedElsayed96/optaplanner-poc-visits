package com.gizsystems.optaplannerpoc.services;

import com.gizsystems.optaplannerpoc.model.Task;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TimeTableConstraintProvider implements ConstraintProvider {

    private static int getMinuteOverlap(Task shift1, Task shift2) {

        var shift1Start = shift1.getStartTime();
        var shift1End = shift1.getEndTime();
        var shift2Start = shift2.getStartTime();
        var shift2End = shift2.getEndTime();
        if(shift1Start == null || shift1End == null || shift2Start == null || shift2End == null) return 1;
        return (int) Duration.between((shift1Start.isAfter(shift2Start)) ? shift1Start : shift2Start,
                (shift1End.isBefore(shift2End)) ? shift1End : shift2End).toMinutes();
    }
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
//                agentTaskConflict(constraintFactory),
                customerTaskConflict(constraintFactory),
                mustSupportService(constraintFactory),
                pwdConstraint(constraintFactory),
                notpwdConstraint(constraintFactory),


        };
    }

//    private Constraint agentTaskConflict(ConstraintFactory constraintFactory) {
//        // An agent can't handle two visits at the same time
//        return constraintFactory
//                .forEach(Task.class)
//                .join(Task.class,
//                        Joiners.equal(Task::getAgent),
//                        Joiners.overlapping(Task::getStartTime, Task::getEndTime))
//                .penalize(HardSoftScore.ONE_HARD, TimeTableConstraintProvider::getMinuteOverlap)
//                .asConstraint("agent Visit conflict");
//    }

    private Constraint mustSupportService(ConstraintFactory constraintFactory) {
        // A teacher can teach at most one lesson at the same time.
        return constraintFactory.forEach(Task.class)
                .filter(task -> !task.getAgent().getSupportedServices().contains(task.getVisit().getService()) )
                .penalize(HardSoftScore.ONE_HARD, value -> 1000_000)
                .asConstraint("agent missing service");
    }

    private Constraint customerTaskConflict(ConstraintFactory constraintFactory) {
        // A student can attend at most one lesson at the same time.
        return constraintFactory.forEach(Task.class)
                .join(Task.class,
                        Joiners.equal(Task::getVisit),
                        Joiners.lessThan(Task::getId))
                .penalize(HardSoftScore.ONE_HARD, (task, task2) -> 1000_000)
                .asConstraint("customer visit duplication");
    }
    private Constraint pwdConstraint(ConstraintFactory constraintFactory) {
        // A non pwd agent can't handle pwd visitor.
        return constraintFactory
                .forEach(Task.class)
                .filter(task -> task.getVisit().isPwd()  && !task.getAgent().isSupportPwd())
                .penalize(HardSoftScore.ONE_HARD, value -> 1000_000)
                .asConstraint("pwd conflict");
    }
    private Constraint notpwdConstraint(ConstraintFactory constraintFactory) {
        // A non pwd agent can't handle pwd visitor.
        return constraintFactory
                .forEach(Task.class)
                .filter(task -> !task.getVisit().isPwd()  && task.getAgent().isSupportPwd())
                .penalize(HardSoftScore.ONE_SOFT, value -> 1000)
                .asConstraint("not pwd constraint");
    }


}
