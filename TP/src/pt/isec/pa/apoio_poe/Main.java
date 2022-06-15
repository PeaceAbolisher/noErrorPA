package pt.isec.pa.apoio_poe;

import pt.isec.pa.apoio_poe.model.fsm.PoeContext;
import pt.isec.pa.apoio_poe.ui.text.PoeUi;

public class Main {
    public static void main(String[] args) {
        PoeContext fsm = new PoeContext();
        PoeUi ui = new PoeUi(fsm);
        System.out.println("Bem vindos, o Processo de Gestão de PoEs vai começar brevemente...");
        ui.start();
    }
}
