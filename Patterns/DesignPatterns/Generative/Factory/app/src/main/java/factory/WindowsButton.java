package factory;

public class WindowsButton implements Button {

    @Override
    public void render() {
        System.out.println("Render win button");
    }

    @Override
    public void onClick(Object runnable) {
        System.out.println("Win button on click");
    }

}
