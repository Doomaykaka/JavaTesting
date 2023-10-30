package bridge;

public class OldRemote extends Remote {

    public OldRemote(Device device) {
        super(device);
    }
    
    public void togglePower() {
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (device.isEnabled()) {
            device.disable();
        } else {
            device.enable();
        }
    }

    public void volumeDown() {
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        device.setVolume(device.getVolume() - 10);
    }

    public void volumeUp() {
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        device.setVolume(device.getVolume() + 10);
    }

    public void channelDown() {
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        device.setChannel(device.getChannel() - 1);
    }

    public void channelUp() {
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        device.setChannel(device.getChannel() + 1);
    }
    
}
