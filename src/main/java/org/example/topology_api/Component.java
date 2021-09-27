package org.example.topology_api;

public class Component {
    private String type;
    private String id;

    public Component(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

}
