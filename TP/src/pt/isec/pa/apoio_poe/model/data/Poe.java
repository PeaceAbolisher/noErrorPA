package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Poe {
    protected List<Internship> internships;
    protected List<Project> projects;
    protected List<SelfProposed> selfProposed;
    protected List<Student> students;
    protected List<Professor> professors;
    protected List<Application> applications;
    protected List<Assignment> assignments;
    protected List<Student> tieBreaker;

    protected boolean configPhaseClosed;
    protected boolean enrollmentPhaseClosed;
    protected boolean atProposalsPhaseClosed;
    protected boolean atOrientPhaseClosed;

    public static final String[] areas = {"DA", "SI", "RAS"};
    public static final String[] courses = {"LEI", "LEI-PL"};

    public Poe() {
        this.internships = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.selfProposed = new ArrayList<>();
        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.assignments = new ArrayList<>();
        this.tieBreaker = new ArrayList<>();
        this.configPhaseClosed = false;
        this.enrollmentPhaseClosed = false;
        this.atProposalsPhaseClosed = false;
        this.atOrientPhaseClosed = false;
    }

    public boolean isConfigPhaseClosed() {
        return configPhaseClosed;
    }

    public void setConfigPhaseClosed(boolean configPhaseClosed) {
        this.configPhaseClosed = configPhaseClosed;
    }

    public boolean isEnrollmentPhaseClosed() {
        return enrollmentPhaseClosed;
    }

    public void setEnrollmentPhaseClosed(boolean enrollmentPhaseClosed) {
        this.enrollmentPhaseClosed = enrollmentPhaseClosed;
    }

    public boolean isAtProposalsPhaseClosed() {
        return atProposalsPhaseClosed;
    }

    public void setAtProposalsPhaseClosed(boolean atProposalsPhaseClosed) {
        this.atProposalsPhaseClosed = atProposalsPhaseClosed;
    }

    public void setAtOrientPhaseClosed(boolean atOrientPhaseClosed) {
        this.atOrientPhaseClosed = atOrientPhaseClosed;
    }

    public void clearTieBreaker(){
        tieBreaker.clear();
    }

    public void addTieBreakerStudent(Student student){
        tieBreaker.add(student);
    }

    public void setTieBreakerAssignment(Assignment tieBreakerAssignment) {
        //this.tieBreakerAssignment = tieBreakerAssignment;
    }

    public Student searchStudent(long num){
        Student cloneStudent = null;
        for(Student s : students){
            if(s.getStd_number() == num){
                try{
                    cloneStudent = (Student) s.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
                break;
            }
        }
        return cloneStudent;
    }

    public Student searchStudentOrigin(long num){
        for(Student s : students){
            if(s.getStd_number() == num){
                return s;
            }
        }
        return null;
    }

    public Professor searchProfessor(String email){
        Professor cloneProfessor = null;
        for(Professor p : professors){
            if(p.getEmail().equalsIgnoreCase(email)){
                try{
                    cloneProfessor = (Professor) p.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
                break;
            }
        }
        return cloneProfessor;
    }

    public Professor searchProfessorOrigin(String email){
        for(Professor p : professors){
            if(p.getEmail().equalsIgnoreCase(email)){
                return p;
            }
        }
        return null;
    }

    public Internship searchInternship(String code){
        Internship cloneInternship = null;
        for(Internship i : internships){
            if(i.getCode().equalsIgnoreCase(code)){
                try{
                    cloneInternship = (Internship) i.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
                break;
            }
        }
        return cloneInternship;
    }

    public Internship  searchInternshipOrigin(String code){
        for(Internship i : internships){
            if(i.getCode().equalsIgnoreCase(code)){
                return i;
            }
        }
        return null;
    }

    public Project searchProject(String code){
        Project cloneProject = null;
        for(Project p : projects){
            if(p.getCode().equalsIgnoreCase(code)){
                try{
                    cloneProject = (Project) p.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
                break;
            }
        }
        return cloneProject;
    }

    public Project  searchProjectOrigin(String code){
        for(Project p : projects){
            if(p.getCode().equalsIgnoreCase(code)){
                return p;
            }
        }
        return null;
    }

    public SelfProposed searchSelfProposed(String code){
        SelfProposed cloneSelfProposed = null;
        for(SelfProposed sp : selfProposed){
            if(sp.getCode().equalsIgnoreCase(code)){
                try{
                    cloneSelfProposed = (SelfProposed) sp.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
                break;
            }
        }
        return cloneSelfProposed;
    }

    public SelfProposed  searchSelfProposedOrigin(String code){
        for(SelfProposed sp : selfProposed){
            if(sp.getCode().equalsIgnoreCase(code)){
                return sp;
            }
        }
        return null;
    }

    public Application searchApplication(long std_number){
        Application cloneApplication = null;
        for(Application a : applications){
            if(a.getStd_number() == std_number){
                try {
                    cloneApplication = (Application) a.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        }
        return cloneApplication;
    }

    public Application searchApplicationOrigin(long std_number){
        for(Application a : applications){
            if(a.getStd_number() == std_number){
               return a;
                }
            }
        return null;
    }

    public Assignment searchAssignment(long std_number){
        Assignment cloneAssignment = null;
        for(Assignment a : assignments){
            if(a.getStd_number() == std_number){
                try {
                    cloneAssignment = (Assignment) a.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        }
        return cloneAssignment;
    }

    public boolean removeStudent(Student s){
        boolean outcome;
        outcome= students.remove(s);
        return outcome;
    }

    public List<Student> listStudent(){
        List<Student> st = new ArrayList<>();
        for(Student s : students){
            try{
                st.add((Student)s.clone());
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
        return st;
    }

    public List<Professor> listProfessor(){
        List<Professor> pr = new ArrayList<>();
        for(Professor p : professors){
            try{
                pr.add((Professor)p.clone());
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
        return pr;
    }

    public List<Internship> listInternship(){
        List<Internship> in = new ArrayList<>();
        for(Internship i : internships){
            try{
                in.add((Internship)i.clone());
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
        return in;
    }

    public List<Project> listProject(){
        List<Project> pr = new ArrayList<>();
        for(Project p : projects){
            try{
                pr.add((Project)p.clone());
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
        return pr;
    }

    public List<SelfProposed> listSelfProposed(){
        List<SelfProposed> sp = new ArrayList<>();
        for(SelfProposed s : selfProposed){
            try{
                sp.add((SelfProposed)s.clone());
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
        return sp;
    }

    public List<Student> listSelfProposedStudent(){
        List<Student> selfProposedStudents = new ArrayList<>();
        for (SelfProposed sp : selfProposed) {
            selfProposedStudents.add(searchStudent(sp.getStd_number()));
        }
        return selfProposedStudents;
    }

    public List<Student> listStudentWithApplication(){
        List<Student> studentsWithApplication = new ArrayList<>();
        for(int i = 0; i < applications.size(); i++){
            Application a = applications.get(i);
            studentsWithApplication.add(searchStudent(a.getStd_number()));
        }
        return studentsWithApplication;
    }

    public List<Student> listStudentWithoutApplication(){
        List<Student> studentsWithoutApplication = new ArrayList<>();
        List<Long> std_numbers = new ArrayList<>();
        int count;
        for(int i = 0; i < applications.size(); i++){
            Application a = applications.get(i);
            std_numbers.add(a.getStd_number());
        }
        for(int i = 0; i < students.size(); i++){
            count = 0;
            Student std = students.get(i);
            for (Long std_number : std_numbers) {
                if (std.getStd_number() == std_number)
                    count++;
            }
            if(count == 0)
                studentsWithoutApplication.add(searchStudent(std.getStd_number()));
        }
        return studentsWithoutApplication;
    }

    public List<Student> listStudentWithSelfProposedAttributed(){
        List<Student> studentWithSelfProposedAttributed = new ArrayList<>();
        List<String> sp = new ArrayList<>();
        for(int i = 0; i < selfProposed.size(); i++){
            if(!sp.contains(selfProposed.get(i).getCode())){
                sp.add(selfProposed.get(i).getCode());
            }
        }
        for(int i = 0; i < assignments.size(); i++){
            for(int j= 0; j < sp.size(); j++){
                if(sp.get(j).equalsIgnoreCase(assignments.get(i).getCode())){
                    studentWithSelfProposedAttributed.add(searchStudent(assignments.get(i).getStd_number()));
                    break;
                }
            }
        }
        return studentWithSelfProposedAttributed;
    }

    public List<Application> listApplications(){
        List<Application> ap = new ArrayList<>();
        for(Application a : applications){
            try{
                ap.add((Application) a.clone());
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
        return ap;
    }

    public List<Internship> listInternshipsWithApplication(){
        List<Internship> internshipsWithApplication = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        for(Application a : applications){
            for (Internship i : internships){
                Iterator<String> it = a.getCode().iterator();
                while(it.hasNext()){
                    String c = it.next();
                    if(c.equals(i.getCode()) && !codes.contains(c)){
                        try {
                            internshipsWithApplication.add((Internship) i.clone());
                        } catch (CloneNotSupportedException e) {
                            return null;
                        }
                        codes.add(c);
                    }
                }
            }
        }
        return internshipsWithApplication;
    }

    public List<Project> listProjectsWithApplication(){
        List<Project> projectsWithApplication = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        for(Application a : applications){
            for (Project p : projects){
                Iterator<String> it = a.getCode().iterator();
                while(it.hasNext()){
                    String c = it.next();
                    if(c.equals(p.getCode()) && !codes.contains(c)){
                        try {
                            projectsWithApplication.add((Project) p.clone());
                        } catch (CloneNotSupportedException e) {
                            return null;
                        }
                        codes.add(c);
                    }
                }
            }
        }
        return projectsWithApplication;
    }

    public List<Internship> listInternshipsWithoutApplication(){
        List<Internship> internshipsWithoutApplication = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        List<String> intern = new ArrayList<>();
        List<String> delete = new ArrayList<>();
        for(int i = 0; i < applications.size(); i++){
            Application a = applications.get(i);
            for(int j = 0; j < a.getCode().size(); j++){
                if(!codes.contains(a.getCode().get(j)))
                    codes.add(a.getCode().get(j));
            }
        }
        for(int i = 0; i < internships.size(); i++){
            intern.add(internships.get(i).getCode());
        }
        for(int i = 0; i < intern.size(); i++){
            for(int j = 0; j < codes.size(); j++){
                if(intern.get(i).equalsIgnoreCase(codes.get(j))){
                    delete.add(codes.get(j));
                }
            }
        }
        intern.removeAll(delete);
        for(int i = 0; i < intern.size(); i++){
            internshipsWithoutApplication.add(searchInternship(intern.get(i)));
        }
        return internshipsWithoutApplication;
    }

    public List<Project> listProjectsWithoutApplication(){
        List<Project> projectsWithoutApplication = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        List<String> proj = new ArrayList<>();
        List<String> delete = new ArrayList<>();
        for(int i = 0; i < applications.size(); i++){
            Application a = applications.get(i);
            for(int j = 0; j < a.getCode().size(); j++){
                if(!codes.contains(a.getCode().get(j)))
                    codes.add(a.getCode().get(j));
            }
        }
        for(int i = 0; i < projects.size(); i++){
            proj.add(projects.get(i).getCode());
        }
        for(int i = 0; i < proj.size(); i++){
            for(int j = 0; j < codes.size(); j++){
                if(proj.get(i).equalsIgnoreCase(codes.get(j))){
                    delete.add(codes.get(j));
                }
            }
        }
        proj.removeAll(delete);
        for(int i = 0; i < proj.size(); i++){
            projectsWithoutApplication.add(searchProject(proj.get(i)));
        }
        return projectsWithoutApplication;
    }

    public List<Assignment> listAssignments(){
        List<Assignment> as = new ArrayList<>();
        for(Assignment a : assignments){
            try{
                as.add((Assignment) a.clone());
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
        return as;
    }

    public List<Project> listProjectsWithAssignment(){
        List<Project> projectsWithAssignment = new ArrayList<>();
        for(Assignment a : assignments){
            for(Project p : projects){
                if(p.getCode().equalsIgnoreCase(a.getCode())) {
                    try {
                        projectsWithAssignment.add((Project)p.clone());
                    } catch (CloneNotSupportedException e) {
                        return null;
                    }
                }
            }
        }
        return projectsWithAssignment;
    }

    public List<Internship> listInternshipsWithAssignment(){
        List<Internship> internshipsWithAssignment = new ArrayList<>();
        for(Assignment a : assignments){
            for(Internship i : internships){
                if(i.getCode().equalsIgnoreCase(a.getCode())) {
                    try {
                        internshipsWithAssignment.add((Internship) i.clone());
                    } catch (CloneNotSupportedException e) {
                        return null;
                    }
                }
            }
        }
        return internshipsWithAssignment;
    }

    public List<Project> listProjectsWithoutAssignment(){
        List<String> assignmentCodes = new ArrayList<>();
        List<Project> projectsWithouAssignment = new ArrayList<>();
        for(Assignment a : assignments)
            assignmentCodes.add(a.getCode());
        for(Project p : projects)
            if(!assignmentCodes.contains(p.getCode())) {
                try {
                    projectsWithouAssignment.add((Project) p.clone());
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        return projectsWithouAssignment;
    }

    public List<Internship> listInternshipsWithoutAssignment(){
        List<String> assignmentCodes = new ArrayList<>();
        List<Internship> internshipsWithouAssignment = new ArrayList<>();
        for(Assignment a : assignments)
            assignmentCodes.add(a.getCode());
        for(Internship i : internships)
            if(!assignmentCodes.contains(i.getCode())) {
                try {
                    internshipsWithouAssignment.add((Internship) i.clone());
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        return internshipsWithouAssignment;
    }

    public FilteredPoEs listPoeWithCriteria(ArrayList<Integer> ops){
        FilteredPoEs fp = new FilteredPoEs();
        for(int i : ops){
            if(i == 1)
                fp.addsp(listSelfProposed());
            if(i == 2)
                fp.addpp(listProject(), listInternship());
            if(i == 3)
                fp.addpoewap(listProjectsWithApplication(), listInternshipsWithApplication());
            if(i == 4)
                fp.addpoewithoutap(listProjectsWithoutApplication(), listInternshipsWithoutApplication());
        }
        return fp;
    }

    public FilteredPoEs listPoeWithCriteria3rdPhase(ArrayList<Integer> ops){
        FilteredPoEs fp = new FilteredPoEs();
        for(int i : ops){
            if(i == 1)
                fp.addsp(listSelfProposed());
            if(i == 2)
                fp.addpp(listProject(), listInternship());
            if(i == 3)
                fp.addpoewa(listProjectsWithAssignment(), listInternshipsWithAssignment());
            if(i == 4)
                fp.addpoewithouta(listProjectsWithoutAssignment(), listInternshipsWithoutAssignment());
        }
        return fp;
    }

    public List<Professor> listCoordinators(){
        List<Professor> coordinators = new ArrayList<>();
        for(Professor p: professors){
            if(p.getRole() != null && (p.getRole().equalsIgnoreCase("coordinator") || p.getRole().equalsIgnoreCase("proponent"))){
                try {
                    coordinators.add((Professor) p.clone());
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        }
        return coordinators;
    }

    public List<Student> listStudentWithCoordinator(){
        List<Student> studentsWithCoordinator = new ArrayList<>();
        for(Assignment a : assignments){
            if(a.getCoordinator() != null && !a.getCoordinator().isEmpty()){
                studentsWithCoordinator.add(searchStudent(a.getStd_number()));
            }
        }
        return studentsWithCoordinator;
    }

    public List<Student> listStudentWithoutCoordinator(){
        List<Student> studentsWithoutCoordinator = new ArrayList<>();
        for(Assignment a : assignments){
            if(a.getCoordinator() == null && a.getCoordinator().isEmpty()){
                studentsWithoutCoordinator.add(searchStudent(a.getStd_number()));
            }
        }
        return studentsWithoutCoordinator;
    }

    public float listAverage(){
        int sum = 0;
        float avg = 0;
        for(Professor p : professors){
            sum += p.getnPoes();
        }
        if(professors.size()>0){
            avg = (float)sum / professors.size();
        }
        return avg;
    }

    public int listMax(){
        int max = 0;
        for(Professor p : professors){
            if(p.getnPoes() > max){
                max = p.getnPoes();
            }
        }
        return max;
    }

    public int listMin(){
        int min = 100;
        if(professors.isEmpty())
            min = 0;
        else{
            for(Professor p : professors){
                if(p.getnPoes() < min){
                    min = p.getnPoes();
                }
            }
        }
        return min;
    }

    public List<Student> listStudentsWithAttributedProposals(){
        List<Student> studentsWithAttributedProposals = new ArrayList<>();
        for(Assignment a : assignments){
            studentsWithAttributedProposals.add(searchStudent(a.getStd_number()));
        }
        return studentsWithAttributedProposals;
    }

    public List<Student> listStudentsWithAssignment(){
        List<Student> studentsWithAssignment = new ArrayList<>();
        for(Assignment a : assignments)
            studentsWithAssignment.add(searchStudent(a.getStd_number()));
        return studentsWithAssignment;
    }

    public List<Student> listStudentsWithoutAttributedProposalsWithApplication(){
        List<Student> studentsWithoutAWithAp = new ArrayList<>();
        List<Student> studentsWithoutAssignment = new ArrayList<>();
        List<Student> studentsWithApplication = new ArrayList<>(listStudentWithApplication());
        List<Student> studentsWithAssignment = new ArrayList<>(listStudentsWithAssignment());

        for(Student s : students){
            if(!studentsWithAssignment.contains(s)) {
                try {
                    studentsWithoutAssignment.add((Student) s.clone());
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        }

        for(Student s : studentsWithApplication)
            if(studentsWithoutAssignment.contains(s)) {
                try {
                    studentsWithoutAWithAp.add((Student) s.clone());
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }

        return studentsWithoutAWithAp;
    }

    public List<Internship> listAvailableInternships(){
        List<Internship> availableInternships = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        for(Assignment a : assignments){
            codes.add(a.getCode());
        }
        for(Internship i : internships){
            if(!codes.contains(i.getCode())){
                try {
                    availableInternships.add((Internship) i.clone());
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        }
        return availableInternships;
    }

    public List<Project> listAvailableProjects(){
        List<Project> availableProjects = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        for(Assignment a : assignments){
            codes.add(a.getCode());
        }
        for(Project p : projects){
            if(!codes.contains(p.getCode())){
                try {
                    availableProjects.add((Project) p.clone());
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }
        }
        return availableProjects;
    }

    public List<Internship> listAttributedInternships(){
        List<Internship> attributedInternships = new ArrayList<>();
        for(Assignment a : assignments){
            for(Internship i : internships){
                if(i.getCode().equalsIgnoreCase(a.getCode())) {
                    try {
                        attributedInternships.add((Internship) i.clone());
                    } catch (CloneNotSupportedException e) {
                        return null;
                    }
                }
            }
        }
        return attributedInternships;
    }

    public List<Project> listAttributedProjects(){
        List<Project> attributedProjects = new ArrayList<>();
        for(Assignment a : assignments){
            for(Project p : projects){
                if(p.getCode().equalsIgnoreCase(a.getCode())) {
                    try {
                        attributedProjects.add((Project) p.clone());
                    } catch (CloneNotSupportedException e) {
                        return null;
                    }
                }
            }
        }
        return attributedProjects;
    }

    public boolean addStudent(Student s){
        return students.add(s);
    }

    public boolean addProfessor(Professor professor) {
        return professors.add(professor);}

    public boolean removeProfessor(Professor professor) {
        return professors.remove(professor);
    }

    //Application
    public boolean addApplication(Application a){
        return applications.add(a);
    }

    public boolean deleteApplicationData(Application a){
        return applications.remove(a);
    }

    //Assignment
    public boolean addAssignment(Assignment a){
        return assignments.add(a);
    }
    public boolean removeAssignment(Assignment a){
        return assignments.remove(a);
    }
    public boolean removeAllAssignments(){
        assignments.clear();
        return true;
    }

    public boolean addSelfProposed(SelfProposed sp) {
        return selfProposed.add(sp);
    }

    public boolean removeInternship(Internship i) {
        return internships.remove(i);
    }

    public boolean removeProject(Project p) {
        return projects.remove(p);
    }

    public boolean removeSelfProposed(SelfProposed sp) {
        return selfProposed.remove(sp);
    }

    public boolean addProject(Project project) {return projects.add(project);}

    public boolean addInternship(Internship internship) {return internships.add(internship);}
}