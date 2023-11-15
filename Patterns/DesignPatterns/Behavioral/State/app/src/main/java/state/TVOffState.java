package state;

public class TVOffState implements TVState {

    @Override
    public void doAction(String action) {
        System.out.println("TV power is off you cant: " + action);
    }

}
