package builder;

public interface CarBuilder {
    public CarBuilder reset(); 
    public CarBuilder setSeats(String seats);
    public CarBuilder setEngine(String engine);
    public CarBuilder setTripComputer(String tripComputer);
    public CarBuilder setGPS(String GPS);
    public CarBuilder setAutoType(String autoType);
    public Car build();
}
