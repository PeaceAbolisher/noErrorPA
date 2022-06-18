package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Application;
import pt.isec.pa.apoio_poe.model.data.Poe;
import pt.isec.pa.apoio_poe.model.data.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentPhase extends PoeStateAdaptor{
    public EnrollmentPhase(PoeContext context, Poe poe) {
        super(context, poe);
    }

    @Override
    public PoeState getState() {
        return PoeState.ENROLLMENT;
    }

    @Override
    public boolean next_phase() {
        if(poe.isAtProposalsPhaseClosed()){
            changeState(PoeState.AT_PROPOSALS_BLOCKED);
        }
        changeState(PoeState.AT_PROPOSALS);
        return true;
    }

    @Override
    public boolean close_phase() {
        if(!poe.isConfigPhaseClosed()){
            System.out.println("You have to close the Configuration Phase first!");
            return false;
        }
        poe.setEnrollmentPhaseClosed(true);
        changeState(PoeState.AT_PROPOSALS);
        return true;
    }

    @Override
    public boolean rewind_phase() {
        if(poe.isConfigPhaseClosed()){
            changeState(PoeState.CONFIG_BLOCKED);
            return true;
        }
        changeState(PoeState.CONFIG);
        return true;
    }

    public boolean addApplicationByHand(long std_number, List<String> codes){
        Student s = poe.searchStudentOrigin(std_number);
        if(s == null)
            return false;
        for(Application a : poe.listApplications())
            if(a.getStd_number() == std_number) //student already has an application
                return false;
        for(String c : codes)
            if(poe.searchInternship(c) == null && poe.searchProject(c) == null) //code doesn't exist
                return false;
        Application a = new Application(std_number, codes);
        changeState(PoeState.ENROLLMENT);
        return poe.addApplication(a);
    }

    public boolean editApplicationCodes(long std_number, List<String> codes){
        Application a = poe.searchApplicationOrigin(std_number);
        if(a == null)
            return false;
        else{
            a.clearCodes();
            for(String c : codes)
                a.addCode(c);
            changeState(PoeState.ENROLLMENT);
            return true;
        }
    }

    public boolean deleteApplicationData(long std_number){
        Application a = poe.searchApplicationOrigin(std_number);
        if(a == null)
            return false;
        poe.deleteApplicationData(a);
        changeState(PoeState.ENROLLMENT);
        return true;
    }
}
