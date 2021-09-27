package org.example.topology_api;

import com.google.gson.Gson;

public class API {



    private static String serializeTopology(Topology topology) {
        Gson gson = new Gson();
        String json = gson.toJson(topology);
        return json;
    }

    private static void deserializeTopology() {
        String json = "{'id':\"top1\",'components':[{'type':\"dev1\",'id':\"id1\"}]}";
        Gson gson = new Gson();
        Topology topology = gson.fromJson(json, Topology.class);
        System.out.println(topology.getId());

    }

}
