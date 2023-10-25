package abstractfactory;

public class WinCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Win checkbox paint");
    }
}
