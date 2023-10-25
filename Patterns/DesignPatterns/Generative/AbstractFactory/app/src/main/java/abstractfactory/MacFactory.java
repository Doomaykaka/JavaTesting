package abstractfactory;

public class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbos() {
        return new MacCheckbox();
    }
}
