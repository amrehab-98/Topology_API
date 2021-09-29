package org.example.topology_api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // write your code here
        API api = new API();
        for(int i=0; i<=11; i++){
            Path fileName = Path.of("./files/json files/topology_"+i+".json");
            Topology topology = api.readJSON(fileName);
//            String x = api.writeJSON(topology);
        }

//      delete topologies
        for(int i=1; i<=10; i++){
            List<Topology> x = api.deleteTopolgy("top"+i);
        }
    }
}


