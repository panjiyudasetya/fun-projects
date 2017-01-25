package nl.sense_os.vm_ex_lib;

import static nl.sense_os.vm_ex_lib.Vehicle.SpecialMovementAbility;

/**
 * Created by panjiyudasetya on 1/25/17.
 */

class Car extends Vehicle implements SpecialMovementAbility {
    private int seat;
    private String stirPosition;

    public Car(Category category, Fuel fuel, String brand) {
        super(category, fuel, brand);
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getStirPosition() {
        return stirPosition;
    }

    public void setStirPosition(String stirPosition) {
        this.stirPosition = stirPosition;
    }

    @Override
    public void startEngine() {
        setStarted(true);
        if (getBrand() != null && !getBrand().isEmpty()) {
            System.out.println(String.format("%s engine started", getBrand()));
        } else {
            System.out.println(String.format("Car engine started.", getBrand()));
        }
    }

    @Override
    public void moveWithGear(Gear gear, int speed) throws Exception {
        if (gear == Gear.REVERSE) {
            System.out.println("Be careful! Your car moving backwards.");
        }
        else {
            System.out.println(String.format("A car moving with %s gear.", gear.name().toLowerCase()));
        }
    }

    @Override
    public void moveWhileStanding() throws Exception {
        throw new Exception("Invalid movement! No one car able to move while standing right now!");
    }

    @Override
    public void moveWhileDrifting() throws Exception {
        System.out.println("Warning! Your car moving faster while drifting!");
    }

    @Override
    public String toString() {
        return "Car information :"+
                "\nBrand "+getBrand()+
                "\nCategory "+getCategory().name()+
                "\nFuel "+getFuel().name().toLowerCase()+
                "\nSeat capacity "+ seat + " people"+
                "\nStirPosition " + stirPosition;
    }
}
