package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.util.List;

public interface IPoeState {
    boolean close_phase();
    boolean next_phase();
    boolean rewind_phase();

    //Student
    boolean addStudentByHand(long number, String name, String email, String toUpperCase, String toUpperCase1, double grade, boolean able, boolean print);
    boolean editStudentName(long std_number, String name);
    boolean editStudentArea(long std_number, String area);
    boolean editStudentCourse(long std_number, String course);
    boolean existsStudent(long std_number);
    boolean consultStudent(long std_number);
    boolean deleteDataStudent(long std_number);

    //Application
    boolean addApplicationByFile(String fileName);
    boolean addApplicationByHand(long std_number, List<String> codes);
    boolean editApplicationCodes(long std_number, List<String> codes);
    boolean deleteApplicationData(long std_number);

    //Assignment
    boolean addAssignment(String code, long std_number);
    boolean removeAssignment(long std_number);
    boolean removeAllAssignments();

    //Coordinators
    boolean addCoordinators(String email, String code);

    //Professor
    Professor searchProfessor();
    boolean editProfessorName(String email, String name);
    boolean existsProfessor(String email);
    boolean deleteDataProfessors(String email);
    boolean addProfessorByHand(String name, String email);
    boolean editCoordinatorName(String code, String email);
    boolean deleteCoordinator(String code);

    //PoE's
    boolean addInternshipByHand(String toUpperCase, String toUpperCase1, String token, String token1, long i);
    boolean addProjectByHand(String toUpperCase, String toUpperCase1, String token, String token1, long i);
    boolean addSelfProposedByHand(String toUpperCase, String token, long parseLong);
    boolean editInternshipArea(String code, String[] tokens);
    boolean editInternshipHousingEntity(String code, String housingEntity);
    boolean editInternshipStd(String code, long std_number);
    boolean editProjectArea(String code, String[] tokens);
    boolean editProjectStd(String code, long std_number);
    boolean deleteInternshipData(String code);
    boolean existsInternship(String code);
    boolean existsProject(String code);
    boolean deleteProjectData(String code);
    boolean deleteSelfProposedData(String code);
    boolean existsSelfProposed(String code);
    SelfProposed searchSelfProposed(String code);
    Internship searchInternship(String code);
    Project searchProject(String code);

    //verifies
    boolean verifyArea(String area);
    boolean verifyCourse(String course);
    boolean verifyStdNumber(long std_number);
    boolean verifyEmail(String email);
    boolean verifications(String email_old, String email_new, String emailNew);
    boolean verifyName(String name);

    PoeState getState();
}