package bridge;

public class TV implements Device {
    private boolean isEnabled;
    private int volume;
    private int channel;

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void enable() {
        isEnabled = true;
    }

    @Override
    public void disable() {
        isEnabled = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int percent) {
        if((percent >= 0) && (percent <= 100)) {
            volume = percent;
        }
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        if(channel >= 0) {
            this.channel = channel;
        }
    }

}
