package nl.sense_os.j2v8executor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
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

            return handleScriptResult(scriptResult);
        } finally {
            releaseV8(jsRuntime, v8JsParam);
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
     * @param scriptResult script result
     * @return JSONObject of script result
     * @throws Exception
     */
    private static JSONObject handleScriptResult(@NonNull Object scriptResult) throws Exception {
        // TODO: do we need this check?
        if (!(scriptResult instanceof V8Object)) {
            throw new Exception("Script result is unknown.");
        }

        V8Object v8JsScriptResult = (V8Object) scriptResult;
        JSONObject scriptResultJObject = v8ObjectToJSONObject(v8JsScriptResult);
        v8JsScriptResult.release();
        return scriptResultJObject;
    }

    /**
     * Convert V8Object into JSONObject
     * @param v8Object V8Object
     * @return JSONObject
     */
    private static JSONObject v8ObjectToJSONObject(@NonNull V8Object v8Object) throws Exception {
        JSONObject result = new JSONObject();
        String[] keys = v8Object.getKeys();
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                result.put(key, String.valueOf(v8Object.get(key)));
            }
        } else {
            result.put("value", String.valueOf(v8Object));
        }
        return result;
    }

    /**
     * Release all runtime memory references to avoid {@link OutOfMemoryError}.
     *
     * @param jsRuntime V8 Runtime
     * @param v8Objects V8 objects
     */
    private static void releaseV8(@NonNull V8 jsRuntime, @Nullable V8Object... v8Objects) {
        if (v8Objects != null) {
            for (V8Object v8Object : v8Objects) {
                v8Object.release();
            }
        }
        jsRuntime.release();
    }
}
