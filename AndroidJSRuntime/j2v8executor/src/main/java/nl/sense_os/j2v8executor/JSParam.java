package nl.sense_os.j2v8executor;

import android.support.annotation.NonNull;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;

/**
 * Created by panjiyudasetya on 1/13/17.
 */

public class JSParam {
    private double weight;
    private double distance;
    private String jsCode;

    public JSParam(double weight, double distance, @NonNull String jsCode) {
        this.weight = weight;
        this.distance = distance;
        this.jsCode = jsCode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getJsCode() {
        return jsCode;
    }

    public void setJsCode(String jsCode) {
        this.jsCode = jsCode;
    }

    public V8Object toV8Object(@NonNull V8 jsRuntime) {
        V8Object v8JsParamExports = new V8Object(jsRuntime);
        v8JsParamExports.add("weight", weight);
        v8JsParamExports.add("distance", distance);
        return v8JsParamExports;
    }
}
