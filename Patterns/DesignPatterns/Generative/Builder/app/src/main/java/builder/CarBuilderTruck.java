package builder;

public class CarBuilderTruck implements CarBuilder{
    private String seats;
    private String engine;
    private String tripComputer;
    private String GPS;
    private String autoType;

    @Override
    public CarBuilder reset() {
        this.seats = null;
        this.engine = null;
        this.tripComputer = null;
        this.GPS = null;
        this.autoType = null;
        return this;
    }

    @Override
    public CarBuilder setSeats(String seats) {
        this.seats = seats + " T";
        return this;
    }

    @Override
    public CarBuilder setEngine(String engine) {
        this.engine = engine + " T";
        return this;
    }

    @Override
    public CarBuilder setTripComputer(String tripComputer) {
        this.tripComputer = tripComputer + " T";
        return this;
    }

    @Override
    public CarBuilder setGPS(String GPS) {
        this.GPS = GPS + " T";
        return this;
    }
    
    @Override
    public CarBuilder setAutoType(String autoType) {
        this.autoType = autoType + " T";
        return this;
    }
    
    @Override
    public Car build() {
        Car instance = new Car();
        instance.setAutoType(autoType);
        instance.setEngine(engine);
        instance.setGPS(GPS);
        instance.setSeats(seats);
        instance.setTripComputer(tripComputer);
        instance.setAutoType(autoType);
        return instance;
    }
}
