package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigPhase extends PoeStateAdaptor{

    public ConfigPhase(PoeContext context, Poe poe){
        super(context, poe);
    }

    @Override
    public PoeState getState() {
        return PoeState.CONFIG;
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

    @Override
    public boolean close_phase() {
        //só permite fechar se, para cada ramo, o número total de propostas é igual ou superior ao número de alunos
        poe.setConfigPhaseClosed(true);
        changeState(PoeState.ENROLLMENT);
        return true;
    }

    public boolean verifyStdNumber(long std_number){
        String aux_num = String.valueOf(std_number);
        return aux_num.length() != 10 || aux_num.charAt(0) != '2' || aux_num.charAt(1) != '0';
    }

    public boolean verifyArea(String a) {
        for (int i = 0; i < Poe.areas.length; i++) {
            if (Poe.areas[i].equalsIgnoreCase(a)) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyCourse(String c){
        for(int i = 0; i < Poe.courses.length; i++){
            if(Poe.courses[i].equalsIgnoreCase(c)){
                return false;
            }
        }
        return true;
    }


    public boolean editStudentName(long num, String name){
        Student student = poe.searchStudentOrigin(num);
        if(student ==null){
            return false;
        }else{
            student.setName(name);
            changeState(PoeState.CONFIG);
            System.out.println("here" + student);
            return  true;
        }
    }

    public boolean editStudentArea(long num, String area) {
        Student student = poe.searchStudentOrigin(num);
        if(student ==null){
            return false;
        }else{
            student.setArea(area.toUpperCase());
            changeState(PoeState.CONFIG);
            return  true;
        }
    }

    public boolean editStudentCourse(long num, String course){
        boolean outcome;
        String test;
        Student student = poe.searchStudentOrigin(num);
            test = student.getCourse();
            outcome = student.setCourse(course.toUpperCase());
            if(!outcome && !(test != course)){
                return false;
            }else{
                changeState(PoeState.CONFIG);
                return true;
            }
        }

    public boolean deleteDataStudent(long std_number) {
        boolean outcome;
        Student student = poe.searchStudent(std_number);
            outcome = poe.removeStudent(student);
            if(!outcome){
                return false;
            }else{
                return true;
            }
        }

    public boolean editProfessorName(String email,String name){
        String old_name, new_name;
        Professor prof = poe.searchProfessorOrigin(email);
        old_name = prof.getName();
        new_name =  prof.setName(name);
        if(old_name != new_name){
            changeState(PoeState.CONFIG);
            return true;
        }else{
            return false;
        }
    }


    public boolean addStudentByHand(long std_number, String name, String email,String course, String area, double grade, boolean able, boolean print){
        boolean outcome;
        Student student = new Student(name, email, std_number, area.toUpperCase(), course.toUpperCase(),
                grade, able, false);
        outcome = poe.addStudent(student);
        if(!outcome){
            return false;
        }else{
            changeState(PoeState.CONFIG);
            return true;
        }
    }



    public boolean addProfessorByHand(String name, String email){
        boolean outcome;
        Professor professor = new Professor(name, email, null);
        outcome = poe.addProfessor(professor);
        if(!outcome){
            return false;
        }else{
            changeState(PoeState.CONFIG);
            return true;
        }
    }

    public boolean existsStudent(long std_number){
        Student student = poe.searchStudent(std_number);
        if(student ==null){
            return false;
        }else{
            return true;
        }
    }

    public boolean existsInternship(String code){
        Internship outcome;
        outcome = poe.searchInternship(code);
        if(outcome!= null){
            return true;
        }else{
            return false;
        }
    }

    public boolean existsSelfProposed(String code){
        SelfProposed outcome;
        outcome = poe.searchSelfProposed(code);
        if(outcome!= null){
            return true;
        }else{
            return false;
        }
    }

    public boolean existsProject(String code){
        Project outcome;
        outcome = poe.searchProject(code);
        if(outcome!= null){
            return true;
        }else{
            return false;
        }
    }


    public boolean editInternshipArea(String code, String[] tokens){
        Internship i = poe.searchInternshipOrigin(code);
        if(i == null){
            return false;
        }
        List<String> areas = new ArrayList<>(Arrays.asList(tokens));
        areas.replaceAll(String::toUpperCase);
        i.setArea(areas);
        return true;
    }

    public boolean deleteInternshipData(String code){
        boolean outcome;
        Internship i = poe.searchInternship(code);
        if(i == null) {
            return false;
        }
        outcome =poe.removeInternship(i);
        if(!outcome){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteProjectData(String code){
        boolean outcome;
        outcome = existsProject(code);
        if(!outcome){
            return false;
        }else{
            Project p = poe.searchProject(code);
            outcome =poe.removeProject(p);
            if(!outcome){
                return false;
            }else{
                return true;
            }
        }
    }

    public boolean deleteSelfProposedData(String code){
        boolean outcome;
        outcome = existsSelfProposed(code);
        if(!outcome){
            return false;
        }else{
            SelfProposed sp = poe.searchSelfProposed(code);
            outcome =poe.removeSelfProposed(sp);
            if(!outcome){
                return false;
            }else{
                return true;
            }
        }
    }

    public boolean editInternshipHousingEntity(String code, String housing){
        Internship i = poe.searchInternshipOrigin(code);
        if(i == null){
            return false;
        }
        i.setHousing_entity(housing);
        return true;
    }

    public boolean editInternshipStd(String code, long numb){
        Internship i = poe.searchInternshipOrigin(code);
        if(i == null){
            return false;
        }
        i.setStd_number(numb);
        return true;
    }

    public boolean editProjectArea(String code, String[] tokens){
        Project p = poe.searchProjectOrigin(code);
        if(p == null){
            return false;
        }
        List<String> areas = new ArrayList<>(Arrays.asList(tokens));
        areas.replaceAll(String::toUpperCase);
        p.setArea(areas);
        return true;
    }

    public boolean editProjectStd(String code, long std_number){
        Project p = poe.searchProjectOrigin(code);
        if(p == null)
            return false;
        p.setStd_number(std_number);
        return true;
    }

    public boolean existsProfessor(String email){
        Professor p = poe.searchProfessor(email);
        if(p==null){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteDataProfessors(String email){
        boolean outcome;
        Professor professor = poe.searchProfessor(email);
        outcome = poe.removeProfessor(professor);
        if(!outcome){
            return false;
        }else{
            changeState(PoeState.CONFIG);
            return true;
        }
    }

    public boolean addSelfProposedByHand(String code, String title, long std_number){
        boolean outcome;
        Student s = poe.searchStudent(std_number);
        s.setAlready_proposed(true);
        SelfProposed sp = new SelfProposed(code.toUpperCase(), title, std_number);
        outcome = poe.addSelfProposed(sp);
        if(!outcome){
            return false;
        }else{
            changeState(PoeState.CONFIG);
            return true;
        }
    }

    public boolean addProjectByHand(String code, String area, String title, String email, long std_number) {
        boolean outcome;
        Student s = poe.searchStudent(std_number);
        if (s == null && std_number != -1) {
            return false;
        }

        poe.searchProfessor(email).setRole("PROPONENT");
        poe.searchProfessor(email).addPoe();
        Project project = new Project(code.toUpperCase(), Collections.singletonList(area), title, email, std_number);
        outcome = poe.addProject(project);
        if (!outcome) {
            return false;
        } else {
            changeState(PoeState.CONFIG);
            return true;
        }
    }


    public boolean  addInternshipByHand(String code, String area, String title, String housing_entity, long std_number){
        boolean outcome;
        Internship internship = new Internship(code.toUpperCase(), Collections.singletonList(area), title, housing_entity, std_number);
        outcome = poe.addInternship(internship);
        if(!outcome){
            return false;
        }else{
            changeState(PoeState.CONFIG);
            return true;
        }
    }
}
