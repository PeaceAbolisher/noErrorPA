package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.util.List;

abstract class PoeStateAdaptor implements IPoeState {
    protected PoeContext context;
    protected Poe poe;

    protected PoeStateAdaptor(PoeContext context, Poe poe) {
        this.context = context;
        this.poe = poe;
    }

    protected void changeState(PoeState state) {
        context.changeState(state.createState(context, poe));
    }


    @Override
    public boolean close_phase() {
        return false;
    }

    @Override
    public boolean next_phase() {
        return false;
    }

    @Override
    public boolean rewind_phase() {
        return false;
    }

    @Override
    public PoeState getState() {
        return null;
    }

    //Students
    @Override
    public boolean addStudentByHand(long number, String name, String email, String toUpperCase, String toUpperCase1, double grade, boolean able, boolean print) {
        return false;
    }
    @Override
    public boolean editStudentName(long std_number, String name) {
        return false;
    }
    @Override
    public boolean editStudentArea(long std_number, String area) {
        return false;
    }
    @Override
    public boolean editStudentCourse(long std_number, String course) {
        return false;
    }
    @Override
    public boolean existsStudent(long std_number) {
        return false;
    }

    //verifies
    @Override
    public boolean verifyArea(String area) {
        return false;
    }
    @Override
    public boolean verifyCourse(String course) {
        return false;
    }
    @Override
    public boolean verifyStdNumber(long std_number) {
        return false;
    }
    @Override
    public boolean verifyEmail(String email) {
        return false;
    }
    @Override
    public boolean verifyName(String name) {
        return false;
    }
    @Override
    public boolean consultStudent(long std_number) {
        return false;
    }
    @Override
    public boolean deleteDataStudent(long std_number) {
        return false;
    }


    //Professors
    @Override
    public boolean editProfessorName(String email, String name) {
        return false;
    }
    @Override
    public Professor searchProfessor() {
        return null;
    }
    @Override
    public boolean existsProfessor(String email) {
        return false;
    }
    @Override
    public boolean deleteDataProfessors(String email) {
        return false;
    }
    @Override
    public boolean addProfessorByHand(String name, String email) {
        return false;
    }
    @Override
    public boolean editCoordinatorName(String code, String email) {
        return false;
    }
    @Override
    public boolean deleteCoordinator(String code) {
        return false;
    }

    //Internships
    @Override
    public boolean addInternshipByHand(String toUpperCase, String toUpperCase1, String token, String token1, long i) {
        return false;
    }
    @Override
    public boolean editInternshipArea(String code, String[] tokens) {
        return false;
    }
    @Override
    public boolean editInternshipStd(String code, long std_number) {
        return false;
    }
    @Override
    public boolean editInternshipHousingEntity(String code, String housingEntity) {
        return false;
    }
    @Override
    public boolean deleteInternshipData(String code) {
        return false;
    }
    @Override
    public boolean existsInternship(String code) {
        return false;
    }
    @Override
    public Internship searchInternship(String code) {
        return null;
    }

    //Projects
    @Override
    public boolean addProjectByHand(String toUpperCase, String toUpperCase1, String token, String token1, long i) {
        return false;
    }
    @Override
    public boolean editProjectArea(String code, String[] tokens) {
        return false;
    }
    @Override
    public boolean editProjectStd(String code, long std_number) {
        return false;
    }
    @Override
    public boolean existsProject(String code) {
        return false;
    }
    @Override
    public boolean deleteProjectData(String code) {
        return false;
    }

    @Override
    public Project searchProject(String code) {
        return null;
    }

    //Self-Proposed
    @Override
    public boolean addSelfProposedByHand(String toUpperCase, String token, long parseLong) {
        return false;
    }
    @Override
    public boolean deleteSelfProposedData(String code) {
        return false;
    }
    @Override
    public boolean existsSelfProposed(String code) {
        return false;
    }
    @Override
    public SelfProposed searchSelfProposed(String code) {
        return null;
    }

    //Applications
    @Override
    public boolean addApplicationByFile(String fileName) {
        return false;
    }
    @Override
    public boolean addApplicationByHand(long std_number, List<String> codes) {
        return false;
    }
    @Override
    public boolean editApplicationCodes(long std_number, List<String> codes) {
        return false;
    }
    @Override
    public boolean deleteApplicationData(long std_number) {
        return false;
    }

    //Assignments
    @Override
    public boolean addAssignment(String code, long std_number) {
        return false;
    }
    @Override
    public boolean removeAssignment(long std_number) {
        return false;
    }
    @Override
    public boolean removeAllAssignments() {
        return false;
    }

    //Coordinators
    @Override
    public boolean addCoordinators(String email, String code) {
        return false;
    }


    //Verifications
    @Override
    public boolean verifications(String email_old, String email_new, String emailNew) {
        return false;
    }
}