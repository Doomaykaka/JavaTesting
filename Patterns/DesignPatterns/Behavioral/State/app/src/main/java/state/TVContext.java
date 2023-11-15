package state;

public class TVContext implements TVState {
    
    private TVState tvState;
    
    public TVContext() {
        tvState = new TVOffState();
    }
 
    @Override
    public void doAction(String action) {
        if(action.equals("click power button")) {
            if(tvState.getClass().equals(TVOnState.class)) {
                tvState = new TVOffState();
            } else {
                tvState = new TVOnState();
            }
        } else {
            this.tvState.doAction(action);
        }
    }
 
}
