package idioms;

public enum EnumSingletonUsing {
    INSTANCE;
    private int id;
    
    private EnumSingletonUsing() {
        System.out.println("Enum Singleton here");
        id = (int) Math.random() * 100;
    }
    
    public void sayHello() {
        System.out.println("Hello from " + id);
    }
}
