package org.example.topology_api;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Component {

    protected String type;
    protected String id;
    protected Feature resistance;
    @SerializedName("m(l)")
    protected Feature ml;
    @SerializedName(value = "netlist" )
    protected HashMap<String, String> netList = new HashMap<>();

    public HashMap<String, String> getNetList() {
        return netList;
    }

    private class Feature {
        @SerializedName("default")
        private Float defaultValue;
        private Float min;

        public Float getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(Float defaultValue) {
            this.defaultValue = defaultValue;
        }

        public Float getMin() {
            return min;
        }

        public void setMin(Float min) {
            this.min = min;
        }

        public Float getMax() {
            return max;
        }

        public void setMax(Float max) {
            this.max = max;
        }

        private Float max;
    }

    public void setNetList(String key, String value) {
        this.netList.put(key,value);
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
