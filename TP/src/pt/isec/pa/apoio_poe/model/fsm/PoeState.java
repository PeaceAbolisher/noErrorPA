package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Poe;

public enum PoeState {
    CONFIG, ENROLLMENT, AT_PROPOSALS, AT_ORIENT, CONSULT, CONFIG_BLOCKED, ENROLLMENT_BLOCKED, AT_PROPOSALS_BLOCKED, TIE_RESOLVER_PHASE;

    IPoeState createState(PoeContext context, Poe poe){
        return switch (this){
            case CONFIG -> new ConfigPhase(context, poe);
            case ENROLLMENT -> new EnrollmentPhase(context, poe);
            case AT_PROPOSALS -> new AtProposalsPhase(context, poe);
            case AT_ORIENT -> new AtOrientPhase(context, poe);
            case CONSULT -> new ConsultPhase(context, poe);
            case CONFIG_BLOCKED -> new ConfigPhaseBlocked(context, poe);
            case ENROLLMENT_BLOCKED -> new EnrollmentPhaseBlocked(context, poe);
            case AT_PROPOSALS_BLOCKED -> new AtProposalsPhaseBlocked(context, poe);
            case TIE_RESOLVER_PHASE -> new TieResolverPhase(context, poe);
        };
    }

}
