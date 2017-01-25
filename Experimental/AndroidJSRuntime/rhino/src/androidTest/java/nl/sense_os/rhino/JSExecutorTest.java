package nl.sense_os.rhino;

import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.DisposableObserver;
import static nl.sense_os.rhino.JSExecutor.ExecutorListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by panjiyudasetya on 1/13/17.
 */
@RunWith(AndroidJUnit4.class)
public class JSExecutorTest {
    private JSParam param;
    @Before
    public void setUp() throws Exception {
        param = new JSParam(
            172,
            10,
            new FileReader().readFile("calorie", "js")
        );
    }

    @After
    public void tearDown() {
        param = null;
    }

    @Test
    public void jsRuntimeExecute_Test() throws Exception {
        JSExecutor.execute(param, new ExecutorListener() {
            @Override
            public void onSuccess(JSONObject result) throws Exception {
                System.out.println("===================== " + result.toString());
                assertEquals(result.getString("burned_cal"), "1123.16");
                assertEquals(result.getString("message"), "Your my hero! Have a jelly doughnut.");
            }

            @Override
            public void onError(Exception ex) {
                assertNull(ex);
            }
        });
    }

    @Test
    public void jsRuntimeExecute_Rx_Test() throws Exception {
        RxJSExecutor.execute(param, new DisposableObserver<JSONObject>() {
            @Override
            public void onNext(JSONObject value) {
                try {
                    assertEquals(value.getString("burned_cal"), "1123.16");
                    assertEquals(value.getString("message"), "Your my hero! Have a jelly doughnut.");
                } catch (Exception ex) {
                    assertNull(ex);
                }
            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
