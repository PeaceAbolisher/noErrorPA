package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Poe;

import java.util.Scanner;

public class ConsultPhase extends PoeStateAdaptor{
    public ConsultPhase(PoeContext context, Poe poe) {
        super(context, poe);
    }

    @Override
    public PoeState getState() {
        return PoeState.CONSULT;
    }
}
