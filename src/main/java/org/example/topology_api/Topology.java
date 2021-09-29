package org.example.topology_api;

import java.util.List;

public class Topology {
    private String id;
    private List<Component> components;

    public Topology(String id, List<Component> components) {
        this.id = id;
        this.components = components;
    }

    public boolean equals(Topology t){
        if(t.getId().equals(this.getId())){
            for(int i=0; i<t.getComponents().size(); i++){
                if(!t.getComponents().get(i).equals(this.getComponents().get(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public Topology() {
        this.id = null;
        this.components = null;
    }

    public String getId() {
        return id;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

}
