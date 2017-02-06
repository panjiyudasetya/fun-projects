package nl.sense_os.j2v8executor;

import android.support.annotation.NonNull;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by panjiyudasetya on 1/13/17.
 */

public abstract class JSExecutor {

    public interface ExecutorListener {
        void onSuccess(JSONObject result) throws Exception;
        void onError(Exception ex);
    }

    /**
     * Execute {@link JSParam} as requirements of executing java script on Android Runtime.
     *
     * @param jsParam Java script parameters. It contain some attributes and JS code.
     * @return JSONObject of script result
     * @throws Exception
     */
    public static JSONObject execute(@NonNull JSParam jsParam) throws Exception {
        // Initialize V8 Runtime
        V8 jsRuntime = V8.createV8Runtime();
        MemoryManager memoryManager = new MemoryManager(jsRuntime);
        V8Object v8JsParam = jsParam.toV8Object(jsRuntime);
        try {
            // Exposing java objects to V8 Runtime
            jsRuntime.add("param", v8JsParam);

            // If we don't know what the output of the js code, just execute script directly.
            // But if the output are known, execute js runtime by the output type.
            Object scriptResult = jsRuntime.executeScript(jsParam.getJsCode());

            // It depend on the context, do we allow some null value or not.
            if (scriptResult == null) {
                throw new Exception("No result returned.");
            }

            return handleScriptResult(jsRuntime, scriptResult);
        } finally {
            memoryManager.release();
        }
    }

    /**
     * Execute {@link JSParam} as requirements of executing java script on Android Runtime.
     *
     * @param jsParam   Java script parameters. It contain some attributes and JS code.
     * @param listener Executor listener. It will fire #onSuccess(Result) whenever JSCode has a return object,
     *                  and call #onError(Exception) whenever some Exeption during executing JSCode happened.
     */
    public static void execute(@NonNull JSParam jsParam, @NonNull ExecutorListener listener) {
        try {
            listener.onSuccess(execute(jsParam));
        } catch (Exception ex) {
            listener.onError(ex);
        }
    }

    /**
     * Script result handler.
     *
     * @param v8 V8 runtime
     * @param scriptResult script result
     * @return JSONObject of script result
     * @throws Exception
     */
    private static JSONObject handleScriptResult(@NonNull V8 v8, @NonNull Object scriptResult) throws Exception {
        JSONObject output;
        if (scriptResult instanceof V8Object) {
            output = jsonifyV8Object(v8, (V8Object) scriptResult);
        } else {
            output = new JSONObject().put("value", String.valueOf(scriptResult));
        }

        // TODO : Do we need check the output format ?
        return output;
    }

    /**
     * Convert V8Object into JSONObject
     *
     * @param v8 {@link V8}Runtime
     * @param v8Object parse-able {@link V8Object}
     * @return {@link JSONObject} result
     * @throws JSONException
     */
    private static JSONObject jsonifyV8Object(@NonNull V8 v8, @NonNull V8Object v8Object) throws JSONException {
        JSONObject result;

        // Get JSON class function inside V8 Object
        V8Object json = v8.getObject("JSON");
        // Setup parameters
        V8Array parameters = new V8Array(v8).push(v8Object);
        // Call stringify function on V8 json object
        String stringify = json.executeStringFunction("stringify", parameters);

        // Classify the stringify object either it is a JSONArray or JSONObject
        String firstChar = String.valueOf(stringify.charAt(0));
        if (firstChar.equalsIgnoreCase("[")) {
            JSONArray jsonArray = new JSONArray(stringify);
            result = new JSONObject().put("value", jsonArray);
        } else {
            result = new JSONObject(stringify);
        }
        return result;
    }
}
