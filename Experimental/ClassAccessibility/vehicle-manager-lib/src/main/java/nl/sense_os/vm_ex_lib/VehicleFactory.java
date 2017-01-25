package nl.sense_os.vm_ex_lib;

import static nl.sense_os.vm_ex_lib.Vehicle.SpecialMovementAbility;

/**
 * Created by panjiyudasetya on 1/25/17.
 */

public class VehicleFactory {
    private Vehicle vehicle;

    public void createMotorcycle(Category category, Fuel fuel, String brand, boolean useInjection) throws Exception {
        if (category == Category.A || category == Category.A1) {
            if (fuel != Fuel.DIESEL && fuel != Fuel.SOLAR) {
                Motorcycle motorcycle = new Motorcycle(category, fuel, brand);
                motorcycle.setUseInjectionTechnology(useInjection);

                vehicle = motorcycle;
                return;
            }
            throw new Exception("Invalid fuel!");
        }
        throw new Exception("Invalid category!");
    }

    public void createCar(Category category, Fuel fuel, String brand, int seat, String stirPosition) throws Exception {
        if (category != Category.A && category != Category.A1) {
            Car car = new Car(category, fuel, brand);
            car.setSeat(seat);
            car.setStirPosition(stirPosition);
            vehicle = car;
            return;
        }
        throw new Exception("Invalid category!");
    }

    public void testVehicleMovement(Gear gear, int speed) throws Exception {
        validateAction();
        vehicle.moveWithGear(gear, speed);
    }

    public void stopVehicle() throws Exception {
        validateAction();
        vehicle.moveWithGear(Gear.NEUTRAL, 0);
    }

    public void startVehicle() throws Exception {
        vehicle.startEngine();
    }

    public void testSpecialMovement() throws Exception {
        validateAction();
        if (vehicle instanceof SpecialMovementAbility) {
            ((SpecialMovementAbility)vehicle).moveWhileStanding();
            ((SpecialMovementAbility)vehicle).moveWhileDrifting();
        } else
            throw new Exception("This vehicle doesn't have any special movement ability");
    }

    public String getVehicleInformation() {
        return vehicle.toString();
    }

    private void validateAction() throws Exception {
        if (vehicle == null) {
            throw new Exception("Please call VehicleFactory.initVehicle first before perform this action.");
        }
        if (!vehicle.isStarted()) {
            throw new Exception("Please start the engine first before perform this action.");
        }
    }
}
