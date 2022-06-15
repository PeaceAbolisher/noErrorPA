package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;
import java.util.List;

public class Project implements Cloneable {
    protected String code;
    protected List<String> area;
    protected String title;
    protected String email_professor;
    protected long std_number;

    public Project(String code, List<String> area, String title, String email_professor, long std_number) {
        this.code = code;
        this.area = new ArrayList<>(area);
        this.title = title;
        this.email_professor = email_professor;
        this.std_number = std_number;
    }

    public String getCode() {
        return code;
    }

    public String getEmail_professor() {
        return email_professor;
    }

    public void setArea(List<String> area) {
        this.area = new ArrayList<>(area);
    }

    public long getStd_number() {
        return std_number;
    }

    public void setStd_number(long std_number) {
        this.std_number = std_number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Code: ");
        sb.append(code);
        sb.append("\nArea(s): ");
        for (String a : area) {
            sb.append(a).append("; ");
        }
        sb.append("\nTitle: ").append(title);
        sb.append("\nProfessor: ").append(email_professor);
        sb.append("\nStudent: ");
        if(std_number == -1)
            sb.append("No student associated yet");
        else
            sb.append(std_number);
        sb.append('\n');
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Project p = (Project) super.clone();
        p.area = new ArrayList<>();
        p.area.addAll(area);
        return p;
    }
}
