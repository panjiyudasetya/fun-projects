package nl.sense_os.rhino;

import android.support.annotation.NonNull;

import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

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
        // Initialize Javascript Context
        Context rhinoContext = Context.enter();

        // Turn off optimization to make Rhino Android compatible
        rhinoContext.setOptimizationLevel(-1);

        Scriptable scope = rhinoContext.initStandardObjects();
        try {
            // Exposing Javascript param objects to Javascript Context
            rhinoContext.evaluateString(
                    scope,
                    String.format("var param = %s", jsParam.toJSContextParam()),
                    "execute:",
                    1,
                    null);

            // If we don't know what the output of the js code, just execute script directly.
            // But if the output are known, execute js runtime by the output type.
            Object objectResult = rhinoContext.evaluateString(
                    scope,
                    jsParam.getJsCode(),
                    "execute:",
                    1,
                    null);

            Scriptable scriptResult = (Scriptable) objectResult;

            // It depend on the context, do we allow some null value or not.
            if (scriptResult == null) {
                throw new Exception("No result returned.");
            }

            return handleScriptResult(scriptResult);
        } finally {
            releaseProperties(scope);
            Context.exit();
        }
    }

    /**
     * Script result handler.
     *
     * @param scriptResult script result
     * @return JSONObject of script result
     * @throws Exception
     */
    private static JSONObject handleScriptResult(@NonNull Scriptable scriptResult) throws Exception {
        JSONObject jsonScriptResult = new JSONObject();
        jsonScriptResult.put("burned_cal", String.valueOf(scriptResult.get("burned_cal", scriptResult)));
        jsonScriptResult.put("message", String.valueOf(scriptResult.get("message", scriptResult)));
        return jsonScriptResult;
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
     * Release all runtime memory references to avoid {@link OutOfMemoryError}.
     *
     * @param scriptable Scriptable scope
     */
    private static void releaseProperties(@NonNull Scriptable scriptable) {
        if (ScriptableObject.getPropertyIds(scriptable) != null) {
            for (int i = 0; i < ScriptableObject.getPropertyIds(scriptable).length; i++) {
                ScriptableObject.deleteProperty(scriptable, i);
            }
        }
    }
}
