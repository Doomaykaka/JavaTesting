package builder;

public class Engineer {
    private CarBuilder builder;
    
    public Engineer(CarBuilder builder) {
        this.builder = builder;
    }
    
    public Car buildV3Car() {
        Car buildCar = builder
                .setEngine("Engine V3")
                .setGPS("GPS V1")
                .setSeats("Light seats")
                .setTripComputer("Intel")
                .setAutoType("BMV")
                .build();
        builder.reset();
        return buildCar;
    }
    
    public Car buildV2Car() {
        Car buildCar = builder
                .setEngine("Engine V2")
                .setGPS("GPS V2")
                .setSeats("Default seats")
                .setTripComputer("AMD")
                .setAutoType("Nissan")
                .build();
        builder.reset();
        return buildCar;
    }
    
    
}
