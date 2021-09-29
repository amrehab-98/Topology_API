package org.example.topology_api;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Component {
    public Component(String type, String id, Float defaultValue,Float min, Float max, HashMap<String, String> netList) {
        this.type = type;
        this.id = id;
        if(type.equals("resistor")){
            this.resistance = new Feature(defaultValue, min, max);
            this.ml = null;
        }
        else if(type.equals("nmos")){
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


    public boolean equals(Component c){
        if(c.getId().equals(this.getId()) && c.getType().equals(this.getType()) && c.getNetList().equals(this.getNetList())){
            if (c.getType().equals("resistor")) {
                if (c.getResistance().equals(this.getResistance())){
                    return true;
                }
                else{
                    return false;
                }
            }
            else if(c.getType().equals("nmos")){
                if(c.getMl().equals(this.getMl())){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            return false;
        }
        return false;
    }

    public class Feature {
        @SerializedName("default")
        private Float defaultValue;
        private Float min;
        private Float max;

        public Float getDefaultValue() {
            return defaultValue;
        }

        public Feature(Float defaultValue, Float min, Float max) {
            this.defaultValue = defaultValue;
            this.min = min;
            this.max = max;
        }

        public Float getMin() {
            return min;
        }

        public Float getMax() {
            return max;
        }

        public boolean equals(Feature f){
            if(f.getDefaultValue().equals(this.defaultValue) && f.getMin().equals(this.min) && f.getMax().equals(this.max)){
                return true;
            }
            else{
                return false;
            }
        }

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
