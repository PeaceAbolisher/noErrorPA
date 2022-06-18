package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

public class AtProposalsPhase extends PoeStateAdaptor{
    public AtProposalsPhase(PoeContext context, Poe poe) {
        super(context, poe);
        assignInternshipsAndProjects();
        assignSelfProposed();
        findApplicationStudent();
    }

    @Override
    public PoeState getState() {
        return PoeState.AT_PROPOSALS;
    }

    @Override
    public boolean next_phase() {
        changeState(PoeState.AT_ORIENT);
        return true;
    }

    @Override
    public boolean close_phase() {
        if(!poe.isEnrollmentPhaseClosed()){
            return false;
        }
        poe.setAtProposalsPhaseClosed(true);
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

    //Assignments
    @Override
    public boolean addAssignment(String code, long std_number) {
        Student s = poe.searchStudent(std_number);
        if(s == null)
            return false;
        Internship i = poe.searchInternship(code);
        Project p = poe.searchProject(code);
        if(i == null && p == null)
            return false;
        if(i != null && !s.isAble())
            return false;
        Assignment a = new Assignment(std_number, code, null);
        return poe.addAssignment(a);
    }

    @Override
    public boolean removeAssignment(long std_number) {
        Student s = poe.searchStudent(std_number);
        if(s == null)
            return false;
        Assignment a = poe.searchAssignment(std_number);
        if(a == null)
            return false;
        return poe.removeAssignment(a);
    }

    @Override
    public boolean removeAllAssignments() {
        return poe.removeAllAssignments();
    }

    public void assignInternshipsAndProjects(){
        for(int i = 0; i < poe.listProject().size(); i++){
            boolean aux = false;
            for(int j = 0; j < poe.listAssignments().size(); j++){
                if(poe.listAssignments().get(j).getCode().equalsIgnoreCase( poe.listProject().get(i).getCode())){
                    aux = true;
                    break;
                }
            }
            if(poe.listProject().get(i).getStd_number() != -1 && !aux){
                Assignment assignment = new Assignment(poe.listProject().get(i).getStd_number(),poe.listProject().get(i).getCode(), null);
                poe.addAssignment(assignment);
                changeState(PoeState.AT_PROPOSALS);
            }
        }

        for(int i = 0; i < poe.listInternship().size(); i++ ){
            boolean aux = false;
            for(int j = 0; j < poe.listInternship().size(); j++){
                if(poe.listInternship().get(j).getCode().equalsIgnoreCase( poe.listInternship().get(i).getCode())){
                    aux = true;
                    break;
                }
            }
            if(poe.listInternship().get(i).getStd_number() != -1 && !aux){
                Assignment assignment = new Assignment(poe.listInternship().get(i).getStd_number(),poe.listInternship().get(i).getCode(), null);
                poe.addAssignment(assignment);
                changeState(PoeState.AT_PROPOSALS);
            }
        }
    }

    public void assignSelfProposed(){
        for(int i = 0; i < poe.listSelfProposed().size(); i++){
            boolean aux = false;
            for(int j = 0; j < poe.listAssignments().size(); j++){
                if(poe.listAssignments().get(j).getCode().equalsIgnoreCase( poe.listSelfProposed().get(i).getCode())){
                    aux = true;
                    break;
                }
            }
            if(aux)
                continue;
            Assignment assignment = new Assignment(poe.listSelfProposed().get(i).getStd_number(),poe.listSelfProposed().get(i).getCode(), null);
            poe.addAssignment(assignment);
            changeState(PoeState.AT_PROPOSALS);
        }
    }

    public void findApplicationStudent(){
        for(Application a : poe.listApplications()) {
            for(Student s : poe.listStudent()) {
                if (a.getStd_number() == s.getStd_number()) {
                    AddPoEToFreeStudents(s, a);
                    break;
                }
            }
        }
    }

    private boolean AddPoEToFreeStudents(Student student, Application application){
        boolean aux = false;
        for(int i = 0; i < application.getCode().size(); i++){
            for(Assignment assignment : poe.listAssignments()){
                if(assignment.getCode().equalsIgnoreCase(application.getCode().get(i))){
                    Student std = poe.searchStudent(assignment.getStd_number());
                    if(student.getGrade() > std.getGrade()){
                        assignment.setStd_number(student.getStd_number());
                        AddPoEToFreeStudents(std, findStudentApplication(std.getStd_number()));
                        for(int j = 0; j < poe.listProject().size(); j++){
                            if(poe.listProject().get(j).getCode().equalsIgnoreCase(application.getCode().get(i))){
                                poe.listProject().get(j).setStd_number(student.getStd_number());
                                aux = true;
                            }
                        }
                        if(!aux && !student.isAble()){
                            return false;
                        }
                        for(int j = 0; j < poe.listInternship().size(); j++){
                            if(poe.listInternship().get(j).getCode().equalsIgnoreCase(application.getCode().get(i)))
                                poe.listInternship().get(j).setStd_number(student.getStd_number());
                        }
                        return true;
                    }
                    if(student.getGrade() == std.getGrade()){
                        Application std_application = findStudentApplication(std.getStd_number());
                        for(int j = 0; j < std_application.getCode().size(); j++){
                            if(std_application.getCode().get(j).equalsIgnoreCase(application.getCode().get(i))){
                                poe.clearTieBreaker();
                                poe.addTieBreakerStudent(student);
                                poe.addTieBreakerStudent(std);
                                poe.setTieBreakerAssignment(assignment);
                                changeState(PoeState.TIE_RESOLVER_PHASE);
                            }
                        }
                    }
                }
                else{
                    Assignment a = new Assignment(student.getStd_number(), application.getCode().get(i), null);
                    poe.addAssignment(a);
                    changeState(PoeState.AT_PROPOSALS);
                    for(int j = 0; j < poe.listProject().size(); j++){
                        if(poe.listProject().get(j).getCode().equalsIgnoreCase(application.getCode().get(i)))
                            poe.listProject().get(j).setStd_number(student.getStd_number());
                    }
                    for(int j = 0; j < poe.listInternship().size(); j++){
                        if(poe.listInternship().get(j).getCode().equalsIgnoreCase(application.getCode().get(i)))
                            poe.listInternship().get(j).setStd_number(student.getStd_number());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private Application findStudentApplication(long std_number){
        for(Application a : poe.listApplications()){
            if(a.getStd_number() == std_number){
                return a;
            }
        }
        return null;
    }
}
