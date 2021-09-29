package org.example.topology_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class API {
    List<Topology> topologies;

    public API() {
        topologies = new ArrayList<>();
    }

    public List<Topology> queryTopologies() {
        return topologies;
    }

    public List<Topology> deleteTopolgy(String topologyId){
        topologies.removeIf(t -> t.getId().equals(topologyId));
        return topologies;
    }
    public List<Component> queryDevices(String topologyId){
        for (Topology t : topologies) {
            if (t.getId().equals(topologyId)) {
                return t.getComponents();
            }
        }
        return Collections.emptyList();
    }

    public List<Component> queryDevicesWithNetlistNode(String topologyId, String netListNodeId){
        List<Component> deviceList = new ArrayList<>();
        for (Topology t : topologies) {
            if (t.getId().equals(topologyId)) {
                for(Component c: t.getComponents()){
                    if(c.getId().equals(netListNodeId)){
                        deviceList.add(c);
                    }
                }
            }
        }
        return deviceList;
    }
// should take a topology and return json file
    public String writeJSON(Topology topology) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(topology);
        //
        Path path = Paths.get("./files/output files/output.json");
        try {
            Files.writeString(path, json, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            // Handle exception
        }
        //
        return json;
    }
// should take a json file and return a topology object (or add it to topology list)
    public Topology readJSON(Path fileName) throws IOException {
        String json = Files.readString(fileName);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Topology topology = gson.fromJson(json, Topology.class);
        topologies.add(topology);
        return topology;
    }

}
