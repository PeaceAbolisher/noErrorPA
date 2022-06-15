package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.util.ArrayList;
import java.util.List;

public class PoeContext {
    private Poe poe;
    private IPoeState state;

    public PoeContext() {
        poe = new Poe();
        state = new ConfigPhase(this, poe);
    }

    void changeState(IPoeState state) {
        this.state = state;
    }

    public Poe getPoe() {
        return poe;
    }

    public PoeState getState() {
        return state.getState();
    }

    public boolean close_phase() {
        return state.close_phase();
    }

    public boolean next_phase() {
        return state.next_phase();
    }

    public boolean rewind_phase() {
        return state.rewind_phase();
    }

    //Filtered PoEs
    public FilteredPoEs listPoesWithCriteria(ArrayList<Integer> options){
        return poe.listPoeWithCriteria(options);
    }

    public FilteredPoEs listPoesWithCriteria3rdPhase(ArrayList<Integer> options){
        return poe.listPoeWithCriteria3rdPhase(options);
    }


    //Student
    public boolean addStudentByHand(long number, String name, String email, String toUpperCase, String toUpperCase1, double grade, boolean able, boolean print) {
        return state.addStudentByHand(number,name, email,toUpperCase,toUpperCase1,grade,able,print);
    }

    public boolean editStudentName(long std_number, String name) {
        return state.editStudentName(std_number,name);
    }

    public boolean editStudentArea(long std_number, String area) {
        return state.editStudentArea(std_number,area);
    }

    public boolean editStudentCourse(long std_number, String course) {
        return state.editStudentCourse(std_number,course);
    }

    public boolean existsStudent(long std_number) {
        return state.existsStudent(std_number);
    }

    public boolean deleteDataStudent(long std_number) {
        return state.deleteDataStudent(std_number);
    }

    public List<Student> listStudentWithCoordinator(){
        return poe.listStudentWithCoordinator();
    }

    public List<Student> listStudentWithoutCoordinator(){
        return poe.listStudentWithoutCoordinator();
    }

    public List<Student> listStudentsWithAttributedProposals(){
        return poe.listStudentsWithAttributedProposals();
    }

    public List<Student> listStudentsWithoutAttributedProposalsWithApplication(){
        return poe.listStudentsWithoutAttributedProposalsWithApplication();
    }

    public List<Student> listSelfProposedStudents(){
        return poe.listSelfProposedStudent();
    }

    public List<Student> listStudentsWithApplication(){
        return poe.listStudentWithApplication();
    }

    public List<Student> listStudentsWithoutApplication(){
        return poe.listStudentWithoutApplication();
    }

    public List<Student> listStudentsWithSelfProposedAttributed(){
        return poe.listStudentWithSelfProposedAttributed();
    }

    public List<Student> listStudents(boolean print){
        if(!print)
            return null;
        return poe.listStudent();
    }

    public Student searchStudent(long std_number){
        return poe.searchStudent(std_number);
    }


    //Professor
    public boolean editProfessorName(String email, String name) {
        return state.editProfessorName(email,name);
    }

    public boolean existsProfessor(String email) {
        return state.existsProfessor(email);
    }

    public boolean deleteDataProfessors(String email) {
        return state.deleteDataProfessors(email);
    }

    public boolean addProfessorByHand(String name, String email) {
        return state.addProfessorByHand( name,email);
    }

    public Professor searchProfessor(String email){
        return poe.searchProfessor(email);
    }

    public float listAverage(){
        return poe.listAverage();
    }

    public int listMax(){
        return poe.listMax();
    }

    public int listMin(){
        return poe.listMin();
    }

    public List<Professor> listCoordinators(){
        return poe.listCoordinators();
    }

    public List<Professor> listProfessors(boolean print){
        if(!print)
            return null;
        return poe.listProfessor();
    }


    //Application
    public boolean addApplicationByFile(String s){
        return state.addApplicationByFile(s);
    }

    public boolean addApplicationByHand(long std_number, List<String> codes){
        return state.addApplicationByHand(std_number, codes);
    }

    public boolean editApplicationCodes(long std_number, List<String> codes){
        return state.editApplicationCodes(std_number, codes);
    }

    public boolean deleteApplicationData(long std_number){
        return state.deleteApplicationData(std_number);
    }

    public Application searchApplication(long std_number){
        return poe.searchApplication(std_number);
    }

    public List<Application> listApplications(boolean print){
        if(!print)
            return null;
        return poe.listApplications();
    }


    //Assignment
    public boolean addAssignment(String code, long std_number){
        return state.addAssignment(code, std_number);
    }

    public boolean removeAssignment(long std_number){
        return state.removeAssignment(std_number);
    }

    public boolean removeAllAssignments(){
        return state.removeAllAssignments();
    }

    public List<Assignment> listAssignments(boolean print){
        if(!print)
            return null;
        return poe.listAssignments();
    }

    public boolean existsInternship(String code) {
        return state.existsInternship(code);
    }


    //SelfProposed
    public boolean existsProject(String code) {
        return state.existsProject(code);
    }

    public boolean existsSelfProposed(String code) {return state.existsSelfProposed(code);}

    public boolean addSelfProposedByHand(String toUpperCase, String token, long parseLong) {
        return state.addSelfProposedByHand(toUpperCase,token,parseLong);
    }

    public List<SelfProposed> listSelfProposed(boolean print){
        if(!print)
            return null;
        return poe.listSelfProposed();
    }

    public boolean deleteSelfProposedData(String code) {
        return state.deleteSelfProposedData(code);
    }

    public SelfProposed searchSelfProposed(String code) {
        return state.searchSelfProposed(code);
    }


    //Internships
    public boolean editInternshipHousingEntity(String code, String housingEntity) {
        return state.editInternshipHousingEntity(code, housingEntity);
    }

    public boolean editInternshipStd(String code, long std_number) {
        return state.editInternshipStd(code, std_number);
    }

    public boolean editInternshipArea(String code, String[] tokens) {
        return state.editInternshipArea(code, tokens);
    }

    public boolean deleteInternshipData(String code) {
        return state.deleteInternshipData(code);
    }

    public boolean addInternshipByHand(String toUpperCase, String toUpperCase1, String token, String token1, long var) {
        return state.addInternshipByHand(toUpperCase, toUpperCase1, token, token1,var);
    }

    public List<Internship> listAttributedInternships(){
        return poe.listAttributedInternships();
    }

    public List<Internship> listAvailableInternships(){
        return poe.listAvailableInternships();
    }

    public List<Internship> listInternships(boolean print){
        if(!print)
            return null;
        return poe.listInternship();
    }

    public Internship searchInternship(String code) {
        return state.searchInternship(code);
    }


    //Projects
    public boolean editProjectArea(String code, String[] tokens) {
        return state.editProjectArea(code, tokens);
    }

    public boolean editProjectStd(String code, long std_number) {
        return state.editProjectStd(code, std_number);
    }

    public boolean deleteProjectData(String code) {
        return state.deleteProjectData(code);
    }

    public boolean addProjectByHand(String toUpperCase, String toUpperCase1, String token, String token1, long i) {
        return state.addProjectByHand( toUpperCase,  toUpperCase1,  token,  token1,  i);
    }

    public List<Project> listAttributedProjects(){
        return poe.listAttributedProjects();
    }

    public List<Project> listAvailableProjects(){
        return poe.listAvailableProjects();
    }

    public List<Project> listProjects(boolean print){
        if(!print)
            return null;
        return poe.listProject();
    }

    public Project searchProject(String code) {
        return state.searchProject(code);
    }


    //Coordinators
    public boolean addCoordinators(String email, String code){
        return state.addCoordinators(email, code);
    }

    public boolean editCoordinatorName(String code, String email){
        return state.editCoordinatorName(code, email);
    }

    public boolean deleteCoordinator(String code){
        return state.deleteCoordinator(code);
    }

    //Verifications
    public boolean verifications(String code,String email_old, String email_new) {
        return state.verifications(code,email_old,email_new);
    }

    public boolean verifyEmail(String email) {
        return state.verifyEmail(email);
    }

    public boolean verifyName(String name) {
        return state.verifyName(name);
    }

    public boolean verifyArea(String area) {
        return state.verifyArea(area);
    }

    public boolean verifyCourse(String course) {
        return state.verifyCourse(course);
    }

    public boolean verifyStdNumber(long std_number) {
        return state.verifyStdNumber(std_number);
    }
}