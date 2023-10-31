package flyweight;

public class CarOptimized {
    private String modelName;
    private String carType;
    private CarEngine engine;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public CarEngine getEngine() {
        return engine;
    }

    public void setEngine(CarEngine engine) {
        this.engine = engine;
    }
}
