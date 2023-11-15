package state;

public class TVOnState implements TVState {

    @Override
    public void doAction(String action) {
        System.out.println("TV: " + action);
    }

}
