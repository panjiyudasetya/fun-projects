package nl.sense_os.classaccessibility;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatDrawableManager;

import nl.sense_os.vm_ex_lib.Category;
import nl.sense_os.vm_ex_lib.Fuel;
import nl.sense_os.vm_ex_lib.Gear;
import nl.sense_os.vm_ex_lib.VehicleFactory;

public class MainActivity extends AppCompatActivity {
    VehicleFactory vehicleFactory = new VehicleFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // In this case, not all classes on vehicle manager will be accessible.
        // Some of them are purposely not to visible on the outside, but the others do.
        doSomethingWithMotorcycle();
        doSomethingWithCar();

        AppCompatDrawableManager.get().getDrawable(this, 0);
    }

    private void doSomethingWithMotorcycle() {
        try {
            vehicleFactory.createMotorcycle(Category.A, Fuel.PERTAMAX, "Kawasaki Ninja 250", true);
            vehicleFactory.startVehicle();
            vehicleFactory.testVehicleMovement(Gear.FIRST, 10);
            vehicleFactory.testVehicleMovement(Gear.SECOND, 70);
            vehicleFactory.testVehicleMovement(Gear.THIRD, 30);
            vehicleFactory.testVehicleMovement(Gear.FORTH, 50);
            vehicleFactory.testSpecialMovement();
            vehicleFactory.stopVehicle();

            System.out.println(vehicleFactory.getVehicleInformation());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doSomethingWithCar() {
        try {
            vehicleFactory.createCar(Category.C, Fuel.PERTAMAX, "Honda HRV", 6, "Right");
            vehicleFactory.startVehicle();
            vehicleFactory.testVehicleMovement(Gear.FIRST, 10);
            vehicleFactory.testVehicleMovement(Gear.SECOND, 70);
            vehicleFactory.testVehicleMovement(Gear.THIRD, 30);
            vehicleFactory.testVehicleMovement(Gear.FORTH, 50);
            vehicleFactory.testVehicleMovement(Gear.NEUTRAL, 0);
            vehicleFactory.testVehicleMovement(Gear.REVERSE, 5);
            vehicleFactory.testSpecialMovement();
            vehicleFactory.stopVehicle();

            System.out.println(vehicleFactory.getVehicleInformation());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
