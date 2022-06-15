package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;
import java.util.List;

public class Application implements Cloneable{
    private long std_number;
    private List<String> code;

    public Application(long std_number, List<String> code) {
        this.std_number = std_number;
        this.code = new ArrayList<>(code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Student: ");
        sb.append(std_number);
        sb.append("\nCode(s): ").append(code);
        sb.append('\n');
        return sb.toString();
    }

    public long getStd_number() {
        return std_number;
    }

    public List<String> getCode() {
        return code;
    }

    public void clearCodes(){
        this.code.clear();
    }

    public void addCode(String code){
        this.code.add(code);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Application a = (Application) super.clone();
        a.code = new ArrayList<>();
        a.code.addAll(code);
        return a;
    }
}