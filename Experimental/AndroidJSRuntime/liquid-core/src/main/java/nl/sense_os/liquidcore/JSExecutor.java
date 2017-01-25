package nl.sense_os.liquidcore;

import android.support.annotation.NonNull;
import org.json.JSONObject;
import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

/**
 * Created by panjiyudasetya on 1/13/17.
 */

public abstract class JSExecutor {
    private static JSContext jsContext;

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
        // Initialize Javascript Context
        JSContext jsContext = initJSContext();
        try {
            // Exposing Javascript param objects to Javascript Context
            // There is different between set properties directly and evaluating java script object
            // So if you want to inject js object into JSContext you can do this :
            jsContext.evaluateScript(String.format("var param = %s ", jsParam.toJSContextParam()));
            // But if you do this :
            // jsContext.property("param", jsParam.toJSContextParam());
            // `param` js object will not recognized on calorie.js

            // If we don't know what the output of the js code, just execute script directly.
            // But if the output are known, execute js runtime by the output type.
            JSValue scriptResult = jsContext.evaluateScript(jsParam.getJsCode());
            // It depend on the context, do we allow some null value or not.
            if (scriptResult == null) {
                throw new Exception("No result returned.");
            }
            return handleScriptResult(scriptResult);
        } finally {
            releaseProperties(jsContext);
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
        } catch (RuntimeException ex) {
            listener.onError(ex);
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
    private static JSONObject handleScriptResult(@NonNull JSValue scriptResult) throws Exception {
        JSObject objScriptResult = scriptResult.toObject();
        JSONObject jsonScriptResult = jsObjectToJSONObject(objScriptResult);
        return jsonScriptResult;
    }

    /**
     * Convert JSObject into JSONObject
     * @param jsObject JSObject
     * @return JSONObject
     */
    private static JSONObject jsObjectToJSONObject(@NonNull JSObject jsObject) throws Exception {
        JSONObject result = new JSONObject();
        if (jsObject.propertyNames() != null && jsObject.propertyNames().length > 0) {
            for (String name : jsObject.propertyNames()) {
                result.put(name, String.valueOf(jsObject.property(name)));
            }
        } else {
            result.put("value", String.valueOf(jsObject));
        }
        return result;
    }

    /**
     * Release all runtime memory references to avoid {@link OutOfMemoryError}.
     *
     * @param jsContext Javascript context
     */
    private static void releaseProperties(@NonNull JSContext jsContext) {
        if (jsContext.propertyNames() != null && jsContext.propertyNames().length > 0) {
            for (String property : jsContext.propertyNames()) {
                jsContext.deleteProperty(property);
            }
        }
    }

    /**
     * Because there is no way to release JSContext functionality, so I think it could be better to make
     * JSContext as singleton to reduce the chance of {@link OutOfMemoryError} issue.
     * @return
     */
    private static JSContext initJSContext() {
        if (jsContext == null) {
            jsContext = new JSContext();
        }
        return jsContext;
    }
}
