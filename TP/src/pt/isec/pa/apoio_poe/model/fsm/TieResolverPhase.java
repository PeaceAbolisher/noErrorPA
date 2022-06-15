package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Poe;
import pt.isec.pa.apoio_poe.model.data.Student;

import java.util.Scanner;

public class TieResolverPhase extends PoeStateAdaptor{
    private Student student;
    private Student std;
    protected TieResolverPhase(PoeContext context, Poe poe) {
        super(context, poe);
        //manageTieBreaker(); //todo
    }

    @Override
    public PoeState getState() {
        return PoeState.TIE_RESOLVER_PHASE;
    }

    @Override
    public boolean rewind_phase() {
        changeState(PoeState.AT_PROPOSALS);
        return true;
    }

}
