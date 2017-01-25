package nl.sense_os.liquidcore;

import android.support.annotation.NonNull;
import org.json.JSONObject;

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

    public String toJSContextParam() throws Exception {
        JSONObject param = new JSONObject();
        param.put("weight", weight);
        param.put("distance", distance);
        return param.toString();
    }
}
