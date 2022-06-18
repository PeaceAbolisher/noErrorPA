package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

public class AtOrientPhase extends PoeStateAdaptor{
    public AtOrientPhase(PoeContext context, Poe poe) {
        super(context, poe);
        addProponentProfessors();
    }

    @Override
    public PoeState getState() {
        return PoeState.AT_ORIENT;
    }

    @Override
    public boolean close_phase() {
        if(!poe.isAtProposalsPhaseClosed()){
            return false;
        }
        poe.setAtOrientPhaseClosed(true);
        changeState(PoeState.CONSULT);
        return true;
    }

    @Override
    public boolean rewind_phase() {
        if(poe.isAtProposalsPhaseClosed()){
            changeState(PoeState.AT_PROPOSALS_BLOCKED);
            return true;
        }
        changeState(PoeState.AT_PROPOSALS);
        return true;
    }

    public void addProponentProfessors(){
        for(Project project : poe.listProject()){
            addCoordinators(project.getEmail_professor(), project.getCode());
        }
    }

    @Override
    public boolean addCoordinators(String email, String code) {
        Professor p = poe.searchProfessor(email);
        if(p == null)
            return false;
        for(Assignment a : poe.listAssignments()){
            if(a.getCode().equalsIgnoreCase(code)){
                if(a.getCoordinator() != null)
                    return false;
                a.setCoordinator(p.getEmail());
                p.setRole("COORDINATOR");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean editCoordinatorName(String code, String email) {
        String old_email;
        Professor p = poe.searchProfessor(email);
        if(p == null)
            return false;
        for(Assignment a : poe.listAssignments()){
            if(a.getCode().equalsIgnoreCase(code)){
                if(searchProject(code) != null) // é um estágio não permite alterar o coordenador
                    return false;
                old_email = a.getCoordinator();
                removeCoordination(old_email);
                a.setCoordinator(p.getEmail());
                if(!p.getRole().equalsIgnoreCase("PROPONENT"))
                    p.setRole("COORDINATOR");
                return true;
            }
        }
        return false;
    }

    public void removeCoordination(String email){
        Professor p = poe.searchProfessor(email);
        p.removePoe();
        if(p.getnPoes() == 0){
            p.setRole("");
        }
    }

    public boolean deleteCoordinator(String code){
        for(Assignment a : poe.listAssignments()){
            if(a.getCode().equalsIgnoreCase(code)){
                if(searchProject(a.getCode()) != null)
                    return false;
                removeCoordination(a.getCoordinator());
                a.setCoordinator("");
                return true;
            }
        }
        return false;
    }

    public boolean verifications(String code, String email_old, String email_new){

         for (int j = 0; j < poe.listAssignments().size(); j++) {
            Assignment assignment = poe.listAssignments().get(j);
            if (assignment.getCode().equalsIgnoreCase(code)){
                email_old = assignment.getCoordinator();
                assignment.setCoordinator(email_new);
            }else{
                return false;
            }
        }

        for (int j = 0; j < poe.listProfessor().size(); j++) {
            Professor p = poe.listProfessor().get(j);
            if(p.getEmail().equalsIgnoreCase(email_new) && (p.getRole() == null || !p.getRole().equalsIgnoreCase("proponent"))){
                p.setRole("coordinator");
                p.addPoe();

            }
            if(p.getEmail().equalsIgnoreCase(email_old) && p.getnPoes() > 0){
                p.removePoe();
            }
            if(p.getnPoes() == 0){
                p.setRole(null);
            }
            else{
                return false;
            }
        }
        return true;
    }
}
