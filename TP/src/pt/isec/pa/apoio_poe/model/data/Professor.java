package pt.isec.pa.apoio_poe.model.data;

public class Professor extends Entity implements Cloneable {
    protected String role;
    protected int nPoes;

    public Professor(String name, String email, String role) {
        super(name, email);
        this.role = role;
        this.nPoes = 0;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role.toLowerCase();
    }

    public Integer getnPoes() {
        return nPoes;
    }

    public void addPoe() {
        this.nPoes++;
    }

    public void removePoe(){
        this.nPoes--;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Professor:\n Name: ");
        sb.append(name);
        sb.append(", Email: ").append(email);
        sb.append(", Role: ").append(role);
        sb.append(", Number of Poes Coordinated: ").append(nPoes);
        sb.append('\n');
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
