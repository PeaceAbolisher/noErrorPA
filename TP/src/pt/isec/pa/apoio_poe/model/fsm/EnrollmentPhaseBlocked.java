package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Poe;

import java.util.Scanner;

public class EnrollmentPhaseBlocked extends PoeStateAdaptor{
    public EnrollmentPhaseBlocked(PoeContext context, Poe poe) {
        super(context, poe);
    }

    @Override
    public PoeState getState() {
        return PoeState.ENROLLMENT_BLOCKED;
    }

    @Override
    public boolean next_phase() {
        if(poe.isAtProposalsPhaseClosed()){
            changeState(PoeState.AT_PROPOSALS_BLOCKED);
        }
        changeState(PoeState.AT_PROPOSALS);
        return true;
    }

    @Override
    public boolean rewind_phase() {
        if(poe.isConfigPhaseClosed()){
            changeState(PoeState.CONFIG_BLOCKED);
            return true;
        }
        changeState(PoeState.CONFIG);
        return true;
    }

}
