package org.example.topologyapi;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

public class Component {
    protected String type;
    protected String id;
    protected Feature resistance;
    @SerializedName("m(l)")
    protected Feature ml;
    @SerializedName(value = "netlist")
    protected HashMap<String, String> netList = new HashMap<>();

    public Component(String type, String id, Float defaultValue,
                     Float min, Float max, HashMap<String, String> netList) {
        this.type = type;
        this.id = id;
        if (type.equals("resistor")) {
            this.resistance = new Feature(defaultValue, min, max);
            this.ml = null;
        } else if (type.equals("nmos")) {
            this.ml = new Feature(defaultValue, min, max);
            this.resistance = null;
        }
        this.netList = netList;
    }

    public Feature getResistance() {
        return resistance;
    }

    public Feature getMl() {
        return ml;
    }

    public HashMap<String, String> getNetList() {
        return netList;
    }


    public boolean equals(Component c) {
        if (c.getId().equals(this.getId()) && c.getType().equals(this.getType())
                && c.getNetList().equals(this.getNetList())) {
            if (c.getType().equals("resistor")) {
                return c.getResistance().equals(this.getResistance());
            } else if (c.getType().equals("nmos")) {
                return c.getMl().equals(this.getMl());
            }
        } else {
            return false;
        }
        return false;
    }

    public void setNetList(String key, String value) {
        this.netList.put(key, value);
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public class Feature {
        @SerializedName("default")
        private final Float defaultValue;
        private final Float min;
        private final Float max;

        public Feature(Float defaultValue, Float min, Float max) {
            this.defaultValue = defaultValue;
            this.min = min;
            this.max = max;
        }

        public Float getDefaultValue() {
            return defaultValue;
        }

        public Float getMin() {
            return min;
        }

        public Float getMax() {
            return max;
        }

        public boolean equals(Feature f) {
            return f.getDefaultValue().equals(this.defaultValue)
                    &&
                   f.getMin().equals(this.min) && f.getMax().equals(this.max);
        }

    }

}
