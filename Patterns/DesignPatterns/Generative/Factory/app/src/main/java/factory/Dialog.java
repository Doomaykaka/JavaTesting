package factory;

public abstract class Dialog implements Button {
    public void render() {
        Button okButton = createButton();
        okButton.onClick(new Object());
        okButton.render();
    }

    public abstract Button createButton();
}
