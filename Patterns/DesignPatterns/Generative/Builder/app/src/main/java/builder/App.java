/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package builder;

public class App {
    public static void main(String[] args) {
        CarBuilder builder;
        
        builder = new CarBuilderLigth();
        Engineer engineer = new Engineer(builder);        
        Car newCarLigth1 = engineer.buildV3Car();     
        Car newCarLigth2 = engineer.buildV2Car();  
        
        builder = new CarBuilderTruck();
        engineer = new Engineer(builder);    
        Car newCarTruck1 = engineer.buildV3Car();
        Car newCarTruck2 = engineer.buildV2Car();
        
        System.out.println(newCarLigth1.toString());
        System.out.println(newCarLigth2.toString());
        System.out.println(newCarTruck1.toString());
        System.out.println(newCarTruck2.toString());
    }
}
