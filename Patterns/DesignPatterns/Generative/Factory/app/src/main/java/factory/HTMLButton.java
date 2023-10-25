package factory;

public class HTMLButton implements Button {

    @Override
    public void render() {
        System.out.println("Render html button");
    }

    @Override
    public void onClick(Object runnable) {
        System.out.println("HTML button on click");
    }

}
