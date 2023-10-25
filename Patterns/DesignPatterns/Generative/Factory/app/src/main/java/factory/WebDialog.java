package factory;

public class WebDialog extends Dialog {

    @Override
    public void onClick(Object runnable) {
        
    }

    @Override
    public Button createButton() {
        return new HTMLButton();
    }

}
