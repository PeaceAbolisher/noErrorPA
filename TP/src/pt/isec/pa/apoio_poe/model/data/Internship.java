package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;
import java.util.List;

public class Internship implements Cloneable {
    protected String code;
    protected List<String> area;
    protected String title;
    protected String housing_entity;
    protected long std_number;

    public Internship(String code, List<String> area, String title, String housing_entity, long std_number) {
        this.code = code;
        this.area = new ArrayList<>(area);
        this.title = title;
        this.housing_entity = housing_entity;
        this.std_number = std_number;
    }

    public String getCode() {
        return code;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public String getHousing_entity() {
        return housing_entity;
    }

    public void setHousing_entity(String housing_entity) {
        this.housing_entity = housing_entity;
    }

    public long getStd_number() {
        return std_number;
    }

    public void setStd_number(long std_number) {
        this.std_number = std_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Internship)) return false;

        Internship that = (Internship) o;

        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
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
        sb.append("\nHousing Entity: ").append(housing_entity);
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
        Internship i = (Internship) super.clone();
        i.area = new ArrayList<>();
        i.area.addAll(area);
        return i;
    }
}
