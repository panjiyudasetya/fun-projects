package nl.sense_os.vm_ex_lib;

/**
 * Created by panjiyudasetya on 1/25/17.
 */

abstract class Vehicle {
    public interface SpecialMovementAbility {
        void moveWhileStanding() throws Exception;
        void moveWhileDrifting() throws Exception;
    }

    private Category category;
    private Fuel fuel;
    private String brand;
    private boolean isStarted;

    public Vehicle() {}

    public abstract void startEngine();
    public abstract void moveWithGear(Gear gear, int speed) throws Exception;

    public Vehicle(Category category, Fuel fuel, String brand) {
        this.category = category;
        this.fuel = fuel;
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    @Override
    public String toString() {
        return "Vehicle information :"+
                "\nBrand "+getBrand()+
                "\nCategory "+getCategory().name()+
                "\nFuel "+getFuel().name().toLowerCase();
    }
}
