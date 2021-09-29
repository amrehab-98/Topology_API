package org.example.topologyapi;

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

/**
 *This API provides the functionality to access, manage and store device topologies.
 *
 * @author Amr Ehab
 */
public class Api {
    List<Topology> topologies;

    /**
     * creates an Api object.
     */
    public Api() {
        this.topologies = new ArrayList<>();
    }

    /**
     * This method is used to query all the topologies stored in the memory.
     *
     * @return a list of topologies stored in memory
     *
     */
    public List<Topology> queryTopologies() {
        return topologies;
    }

    /**
     * This method is used to delete a topology from the memory given its id.
     *
     * @param topologyId the string id of a topology you want to delete
     *
     * @return a list of topologies in the memory after deletion.
     *
     */
    public List<Topology> deleteTopolgy(String topologyId) {
        topologies.removeIf(t -> t.getId().equals(topologyId));
        return topologies;
    }

    /**
     * this method is used to get the list of devices in a topology given its id.
     *
     * @param topologyId the string id of a topology in memory
     *
     * @return list of components in the topology
     */
    public List<Component> queryDevices(String topologyId) {
        for (Topology t : topologies) {
            if (t.getId().equals(topologyId)) {
                return t.getComponents();
            }
        }
        return Collections.emptyList();
    }

    /**
     * this method is to get the device list of a topology given its id and the id of a component.
     *
     * @param topologyId the string id of a topology in memory
     * @param netListNodeId the string id of a component in the topology
     * @return a list of components of the topology
     */
    public List<Component> queryDevicesWithNetlistNode(String topologyId, String netListNodeId) {
        for (Topology t : topologies) {
            if (t.getId().equals(topologyId)) {
                for (Component c : t.getComponents()) {
                    if (c.getId().equals(netListNodeId)) {
                        return t.getComponents();
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    // should take a topology and return json file

    /**
     * this method is used to write a topology to a json file.
     *
     * @param topology topology object to be jsonified
     * @return the path to the json file produced
     */
    public Path writeJSON(Topology topology) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(topology);
        //
        Path path = Paths.get("./files/output files/output.json");
        int i = 0;
        while (Files.exists(path)) {
            path = Paths.get("./files/output files/output_" + i + ".json");
            i++;
        }
        try {
            Files.writeString(path, json, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            // Handle exception
        }
        //
        return path;
    }


    /**
     * this method is used to read a file and parse it into an object and add to topology list.
     *
     * @param filePath the path of the json file to be read
     * @return a topology object produced from the file
     * @throws IOException throws an exception if the file is not found.
     */
    public Topology readJSON(Path filePath) throws IOException {
        if (Files.exists(filePath)) {
            String json = Files.readString(filePath);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Topology topology = gson.fromJson(json, Topology.class);
            topologies.add(topology);
            return topology;
        } else {
            throw new IOException("File doesn't exist");
        }
    }

}
