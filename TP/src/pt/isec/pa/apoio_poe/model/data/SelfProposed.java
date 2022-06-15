package pt.isec.pa.apoio_poe.model.data;

public class SelfProposed {
    protected String code;
    protected String title;
    protected long std_number;

    public SelfProposed(String code, String title, long std_number) {
        this.code = code;
        this.title = title;
        this.std_number = std_number;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public long getStd_number() {
        return std_number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Code: ");
        sb.append(code);
        sb.append("\nTitle: ").append(title);
        sb.append("\nStudent: ").append(std_number);
        sb.append('\n');
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
