package abstractfactory;

public class MacCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Mac checkbox paint");
    }
}
