package abstractfactory;

public class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Mac button paint");
    }
}
