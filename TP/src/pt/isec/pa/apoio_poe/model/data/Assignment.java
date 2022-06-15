package pt.isec.pa.apoio_poe.model.data;

public class Assignment implements Cloneable {
    protected long std_number;
    protected String code;
    protected String coordinator;

    public Assignment(long std_number, String code, String coordinator) {
        this.std_number = std_number;
        this.code = code.toUpperCase();
        this.coordinator = coordinator;
    }

    public long getStd_number() {
        return std_number;
    }

    public String getCode() {
        return code;
    }

    public void setStd_number(long std_number) {
        this.std_number = std_number;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Code: ");
        sb.append(code);
        sb.append("\nStudent: ").append(std_number);
        sb.append("\nCoordinator: ");
        if(coordinator == null){
            sb.append("No coordinator added yet.");
        }
        else{
            sb.append(coordinator);
        }
        sb.append('\n');
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
