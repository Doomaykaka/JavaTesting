package builder;

public class Car {
    private String seats;
    private String engine;
    private String tripComputer;
    private String GPS;
    private String autoType;
    
    public String getSeats() {
        return seats;
    }
    
    public void setSeats(String seats) {
        this.seats = seats;
    }
    
    public String getEngine() {
        return engine;
    }
    
    public void setEngine(String engine) {
        this.engine = engine;
    }
    
    public String getTripComputer() {
        return tripComputer;
    }
    
    public void setTripComputer(String tripComputer) {
        this.tripComputer = tripComputer;
    }
    
    public String getGPS() {
        return GPS;
    }
    
    public void setGPS(String gPS) {
        GPS = gPS;
    }

    public String getAutoType() {
        return autoType;
    }

    public void setAutoType(String autoType) {
        this.autoType = autoType;
    }
    
    public String toString () {
        String representation = "Car info: |";
        
        if(this.autoType != null) {
            representation += " autoType = " + this.autoType + " |";
        }
        if(this.engine != null) {
            representation += " engine = " + this.engine + " |";
        }
        if(this.GPS != null) {
            representation += " GPS = " + this.GPS + " |";
        }
        if(this.seats != null) {
            representation += " seats = " + this.seats + " |";
        }
        if(this.tripComputer != null) {
            representation += " tripComputer = " + this.tripComputer + " |";
        }
        
        return representation;
    }
}
