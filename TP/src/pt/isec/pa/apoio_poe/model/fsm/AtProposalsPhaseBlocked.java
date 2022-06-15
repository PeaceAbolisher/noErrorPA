package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Poe;

public class AtProposalsPhaseBlocked extends PoeStateAdaptor{
    public AtProposalsPhaseBlocked(PoeContext context, Poe poe) {
        super(context, poe);
    }

    @Override
    public PoeState getState() {
        return PoeState.AT_PROPOSALS_BLOCKED;
    }

    @Override
    public boolean next_phase() {
        changeState(PoeState.AT_ORIENT);
        return true;
    }

    @Override
    public boolean rewind_phase() {
        if(poe.isEnrollmentPhaseClosed()){
            changeState(PoeState.ENROLLMENT_BLOCKED);
            return true;
        }
        changeState(PoeState.ENROLLMENT);
        return true;
    }
}
