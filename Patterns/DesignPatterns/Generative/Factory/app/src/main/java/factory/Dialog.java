package factory;

public abstract class Dialog {
    public void render() {
        Button okButton = createButton();
        okButton.onClick(new Object());
        okButton.render();
    }

    public abstract Button createButton();
}
