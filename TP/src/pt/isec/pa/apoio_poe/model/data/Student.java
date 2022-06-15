package pt.isec.pa.apoio_poe.model.data;

public class Student extends Entity implements Cloneable {
    protected long std_number;
    protected String area;
    protected String course;
    protected double grade;
    protected boolean able; //able to acess projects
    protected boolean already_proposed;

    public Student(String name, String email, long std_number, String area, String course, double grade, boolean able, boolean already_proposed) {
        super(name, email);
        this.std_number = std_number;
        this.area = area;
        this.course = course;
        this.grade = grade;
        this.able = able;
        this.already_proposed = already_proposed;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean setCourse(String course) {
        this.course = course;
        return false;
    }

    public long getStd_number() {
        return std_number;
    }

    public boolean isAble() {
        return able;
    }

    public double getGrade() {
        return grade;
    }

    public void setAlready_proposed(boolean already_proposed) {
        this.already_proposed = already_proposed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Student:\n Name: ");
        sb.append(name);
        sb.append(", Email: ").append(email);
        sb.append(", Number: ").append(std_number);
        sb.append(", Area: ").append(area);
        sb.append(", Course: ").append(course);
        sb.append(", Grade: ").append(grade);
        sb.append(", Internship: ").append(able);
        sb.append('\n');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;

        return std_number == student.std_number;
    }

    @Override
    public int hashCode() {
        return (int) (std_number ^ (std_number >>> 32));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
