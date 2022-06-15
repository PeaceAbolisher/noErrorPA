package pt.isec.pa.apoio_poe.model.data;

abstract class Entity {
    protected String name;
    protected String email;

    public Entity(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity entity)) return false;

        return email.equals(entity.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}

