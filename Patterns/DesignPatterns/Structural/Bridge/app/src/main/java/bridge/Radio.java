package bridge;

public class Radio implements Device {

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
    public void setVolume(int volume) {
        if ((volume >= 0) && (volume <= 20)) {
            this.volume = volume;
        }
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        if (channel > 0) {
            this.channel = channel;
        }
    }
}
