package factory;

public class WindowsDialog extends Dialog {

    @Override
    public void onClick(Object runnable) {
        
    }

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

}
