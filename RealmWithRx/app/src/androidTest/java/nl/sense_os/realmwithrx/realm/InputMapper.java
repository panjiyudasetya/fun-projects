package nl.sense_os.realmwithrx.realm;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import nl.sense_os.realmwithrx.realm.objects.RealmInput;

/**
 * Created by panjiyudasetya on 2/23/17.
 */

public class InputMapper {
    public static RealmInput toRealm(Input input) {
        return new RealmInput(input.getName(), input.getVersion());
    }
    public static Input toPojo(RealmInput input) {
        return new Input(input.getName(), input.getVersion());
    }
    public static List<Input> toList(RealmResults<RealmInput> realmInputs) {
        List<Input>  inputs = new ArrayList<>();
        for (RealmInput realmInput : realmInputs) {
            inputs.add(toPojo(realmInput));
        }
        return inputs;
    }
}
