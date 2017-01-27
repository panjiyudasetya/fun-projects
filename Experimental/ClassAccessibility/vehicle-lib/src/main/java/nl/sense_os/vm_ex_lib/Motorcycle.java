package nl.sense_os.vm_ex_lib;

import static nl.sense_os.vm_ex_lib.Vehicle.SpecialMovementAbility;

/**
 * Created by panjiyudasetya on 1/25/17.
 */

class Motorcycle extends Vehicle implements SpecialMovementAbility {
    private static final int STANDING_THRESHOLD_DIFF_VALUE = 20;

    private boolean useInjectionTechnology;
    private int prevSpeed;

    public Motorcycle(Category category, Fuel fuel, String brand) {
        super(category, fuel, brand);
    }

    public boolean isUseInjectionTechnology() {
        return useInjectionTechnology;
    }

    public void setUseInjectionTechnology(boolean useInjectionTechnology) {
        this.useInjectionTechnology = useInjectionTechnology;
    }

    @Override
    public void startEngine() {
        setStarted(true);
        if (getBrand() != null && !getBrand().isEmpty()) {
            System.out.println(String.format("%s engine started", getBrand()));
        } else {
            System.out.println(String.format("Motorcycle engine started.", getBrand()));
        }
    }

    @Override
    public void moveWithGear(Gear gear, int speed) throws Exception {
        if (gear == Gear.REVERSE && getCategory() != Category.B1) {
            throw new Exception("Only motorcycle with three tire are available to do reverse movement");
        }

        if (gear == Gear.REVERSE) {
            System.out.println("Be careful! Your motorcycle moving backwards.");
        }
        else {
            int diff = speed - prevSpeed;
            if (diff > 0 && diff >= STANDING_THRESHOLD_DIFF_VALUE) {
                moveWhileStanding();
            } else {
                System.out.println(String.format("A motorcycle moving with %s gear", gear.name().toLowerCase()));
            }
            prevSpeed = speed;
        }
    }

    @Override
    public void moveWhileStanding() throws Exception {
        System.out.println("Warning! Your motorcycle moving faster while standing!");
    }

    @Override
    public void moveWhileDrifting() throws Exception {
        System.out.println("Warning! Your motorcycle moving faster while drifting!");
    }

    @Override
    public String toString() {
        return "Motorcycle information :"+
                "\nBrand "+getBrand()+
                "\nCategory "+getCategory().name()+
                "\nFuel "+getFuel().name().toLowerCase()+
                (isUseInjectionTechnology() ? "" : "\nWith Injection Technology");
    }
}
