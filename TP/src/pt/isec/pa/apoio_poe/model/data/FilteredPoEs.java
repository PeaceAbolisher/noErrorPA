package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;
import java.util.List;

public class FilteredPoEs {
    List<Project> projects;
    List<Internship> internships;
    List<SelfProposed> selfProposed;

    public FilteredPoEs() {
        projects = new ArrayList<>();
        internships = new ArrayList<>();
        selfProposed = new ArrayList<>();
    }

    public void addsp(List<SelfProposed> sp){
        selfProposed = sp;
    }

    public void addpp(List<Project> p, List<Internship> i){
        projects = p;
        internships = i;
    }

    public void addpoewa(List<Project> pwa, List<Internship> iwa){
        if(projects.isEmpty())
            projects = pwa;
        else{
            for(Project p : pwa){
                if(!projects.contains(p))
                    projects.add(p);
            }
        }
        if(internships.isEmpty())
            internships = iwa;
        else{
            for(Internship i : iwa){
                if(!internships.contains(i))
                    internships.add(i);
            }
        }
    }

    public void addpoewap(List<Project> pwa, List<Internship> iwa){
        if(projects.isEmpty())
            projects = pwa;
        else{
            for(Project p : pwa){
                if(!projects.contains(p))
                    projects.add(p);
            }
        }
        if(internships.isEmpty())
            internships = iwa;
        else{
            for(Internship i : iwa){
                if(!internships.contains(i))
                    internships.add(i);
            }
        }
    }

    public void addpoewithouta(List<Project> pwithouta, List<Internship> iwithouta){
        if(projects.isEmpty())
            projects = pwithouta;
        else{
            for(Project p : pwithouta){
                if(!projects.contains(p))
                    projects.add(p);
            }
        }
        if(internships.isEmpty())
            internships = iwithouta;
        else{
            for(Internship i : iwithouta){
                if(!internships.contains(i))
                    internships.add(i);
            }
        }
    }

    public void addpoewithoutap(List<Project> pwithouta, List<Internship> iwithouta){
        if(projects.isEmpty())
            projects = pwithouta;
        else{
            for(Project p : pwithouta){
                if(!projects.contains(p))
                    projects.add(p);
            }
        }
        if(internships.isEmpty())
            internships = iwithouta;
        else{
            for(Internship i : iwithouta){
                if(!internships.contains(i))
                    internships.add(i);
            }
        }
    }

    // TODO: 07/06/2022 ToString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Projects: ");
        for(Project p : projects){
            sb.append("Code: ").append(p.getCode());
        }
        sb.append("\nInternships: ");
        for(Internship i : internships){
            sb.append("Code: ").append(i.getCode());
        }
        sb.append("Self-proposed: ");
        for(SelfProposed sp : selfProposed){
            sb.append("Code: ").append(sp.getCode());
        }
        sb.append('\n');
        return sb.toString();
    }
}