package org.example.topology_api;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // write your code here
        System.out.println("hello");
        String topologyID = "top1";
        Component device = new Component("dev1", "id1");
        List<Component> components = new ArrayList<Component>();
        components.add(device);
        Topology topology = new Topology(topologyID, components);
    }
}
