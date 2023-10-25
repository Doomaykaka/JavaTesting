package abstractfactory;

public class WinFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbos() {
        return new WinCheckbox();
    }
}
