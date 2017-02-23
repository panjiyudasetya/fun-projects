package nl.sense_os.realmwithrx;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import nl.sense_os.realmwithrx.realm.Input;
import nl.sense_os.realmwithrx.realm.RxRealmService;

import static nl.sense_os.realmwithrx.ThreadHelper.Notifier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RxRealmTest {
    private static final String TEST_CONFIG_NAME = "nl.sense_os.realmwithrx_test";
    private Context context;
    private RxRealmService service;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        service = new RxRealmService(context, TEST_CONFIG_NAME);
    }

    @After
    public void tearDown() {
        service.deleteRealm();
    }

    @Test
    public void testRx_AddInput() throws Exception {
        // Arrange :
        final Notifier notifier = new Notifier();
        Input input = new Input("time_active", 1);
        service.rxCreateOrUpdateInput(input)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        assertTrue(aBoolean);
                        print("ADD NEW INPUT STATUS HAS BEEN CONSUMED");
                        notifier.accept();
                    }
                });
        ThreadHelper.waitUntil(2, notifier);
        service.deleteRealm();
    }

    @Test
    public void testRx_GetInputs() throws Exception {
        // Arrange :
        final Notifier notifier = new Notifier();
        final Input input = new Input("time_active", 2);
        service.rxCreateAndGetInputs(input)
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Input>>() {
                    @Override
                    public void accept(List<Input> value) throws Exception {
                        assertEquals(1, value.size());
                        assertEquals(input.getName(), value.get(0).getName());
                        assertEquals(2, value.get(0).getVersion());
                        print("INPUTS VALUE HAS BEEN CONSUMED");
                        notifier.accept();
                    }
                });
        ThreadHelper.waitUntil(2, notifier);
        service.deleteRealm();
    }

    /** Helper function to print a log message */
    public void print(String message) {
        if(BuildConfig.DEBUG) {
            Log.d("[RXRLTEST]", String.format("========== %s", message));
        }
    }
}
