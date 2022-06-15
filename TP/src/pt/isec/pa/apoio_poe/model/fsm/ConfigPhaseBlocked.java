package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Poe;

public class ConfigPhaseBlocked extends PoeStateAdaptor{
    public ConfigPhaseBlocked(PoeContext context, Poe poe) {
        super(context, poe);
    }

    @Override
    public PoeState getState() {
        return PoeState.CONFIG_BLOCKED;
    }

    @Override
    public boolean next_phase() {
        if(poe.isEnrollmentPhaseClosed()){
            changeState(PoeState.ENROLLMENT_BLOCKED);
            return true;
        }
        changeState(PoeState.ENROLLMENT);
        return true;
    }
}
