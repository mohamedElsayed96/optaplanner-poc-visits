//package com.gizsystems.optaplannerpoc.services.change;
//
//import com.gizsystems.optaplannerpoc.model.EmployeeShift;
//import com.gizsystems.optaplannerpoc.model.Visit;
//import org.acme.callcenter.domain.Call;
//import org.acme.callcenter.domain.CallCenter;
//import org.optaplanner.core.api.solver.change.ProblemChange;
//import org.optaplanner.core.api.solver.change.ProblemChangeDirector;
//
//import java.time.LocalTime;
//
//public class PinCallProblemChange implements ProblemChange<EmployeeShift> {
//
//    private final Visit visit;
//
//    public PinCallProblemChange(Visit visit) {
//        this.visit = visit;
//    }
//
//    @Override
//    public void doChange(EmployeeShift workingCallCenter, ProblemChangeDirector problemChangeDirector) {
//        problemChangeDirector.changeProblemProperty(visit, workingCall -> {
//            workingCall.setPinned(true);
//            workingCall.setPickUpTime(LocalTime.now());
//        });
//    }
//}
