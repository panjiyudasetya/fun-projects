package nl.sense_os.electricity;

import android.support.annotation.RestrictTo;
import android.support.v7.widget.AppCompatDrawableManager;

/**
 * Created by panjiyudasetya on 1/27/17.
 */
@RestrictTo(RestrictTo.Scope.GROUP_ID)
public class ElectricityFactory {

    class MockElectricity extends Electricity {
        
    }

    public void addElectricity() {
        int resId = 0;
        AppCompatDrawableManager.get().getDrawable(null, resId);
    }
}
