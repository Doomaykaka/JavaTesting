package idioms;

public class CloneNotSupportedExceptionAvoid implements Cloneable {
    private String name;

    public CloneNotSupportedExceptionAvoid(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CloneNotSupportedExceptionAvoid clone() {
        CloneNotSupportedExceptionAvoid clonedObject = null;

        try {
            clonedObject = (CloneNotSupportedExceptionAvoid) super.clone();
            clonedObject.setName(this.getName());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedObject;
    }

    @Override
    public String toString() {
        return "CloneNotSupportedExceptionAvoid [name=" + name + "]";
    }
}
