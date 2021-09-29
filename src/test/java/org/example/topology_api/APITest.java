package org.example.topology_api;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class APITest {
    API api = new API();

    @BeforeEach
    public void setupAPI(){
        api = new API();
    }

    @DisplayName("checking if topology from input json equals topology created")
    @Test
    public void shouldReadJSON() throws IOException {
        Path fileName = Path.of("./files/test topologies/topology_test.json");
        Topology topology_one = api.readJSON(fileName);
//        create a resistor component
        HashMap<String,String> netListR = new HashMap<>();
        netListR.put("t1", "vdd");
        netListR.put("t2", "n1");
        Component resistor = new Component("resistor","res1", (float) 900, (float)100, (float)1000, netListR);
//        create an nmos component
        HashMap<String,String> netListN = new HashMap<>();
        netListN.put("drain", "n1");
        netListN.put("gate", "vin");
        netListN.put("source", "vss");
        Component nmos = new Component("nmos","m1", (float) 1.1, (float)1, (float)2.1, netListN);
//        create list of components for topology
        List<Component> listofComponents = new ArrayList<>();
        listofComponents.add(resistor);
        listofComponents.add(nmos);
//        create topology
        Topology topology_two = new Topology("top1",listofComponents);

        assertTrue(topology_two.equals(topology_one));

    }

    @DisplayName("Throws IOException when file not found")
    @Test
    public void shouldThrowAnExceptionIfFileIsNotFound(){
        Path fileName = Path.of("./files/json files/topology_notThere.json");
        Assertions.assertThrows(IOException.class, () -> {
            Topology topology_one = api.readJSON(fileName);
        });
    }

    @DisplayName("can write JSON and re-read it. It should be equal to original value")
    @Test
    public void shouldWriteJSON() throws IOException {
//      create a resistor 1 component
        HashMap<String,String> netListR = new HashMap<>();
        netListR.put("t1Created", "vddCreated");
        netListR.put("t2Created", "n1Created");
        Component resistor1 = new Component("resistor","resCreated1", (float) 800, (float)150, (float)2000, netListR);
//      create resistor 2 component
        netListR = new HashMap<>();
        netListR.put("t1Created2", "vddCreated2");
        netListR.put("t2Created2", "n1Created2");
        Component resistor2 = new Component("resistor","resCreated2", (float) 900, (float)1600, (float)2200, netListR);
//      create an nmos component
        HashMap<String,String> netListN = new HashMap<>();
        netListN.put("drainCreated", "n1Created");
        netListN.put("gateCreated", "vinCreated");
        netListN.put("sourceCreated", "vssCreated");
        Component nmos = new Component("nmos","m1Created", (float) 1.2, (float)1.5, (float)2.1, netListN);
//      create list of components for topology
        List<Component> listofComponents = new ArrayList<>();
        listofComponents.add(resistor1);
        listofComponents.add(resistor2);
        listofComponents.add(nmos);
//        create topology
        Topology topology = new Topology("top1",listofComponents);
//        write JSON
        Path path = api.writeJSON(topology);
//        read JSON
        Topology topology_fromJSON = api.readJSON(path);
        assertTrue(topology_fromJSON.equals(topology));
    }

    @DisplayName("Query topologies. It should get back a list of 5 topologies")
    @Test
    public void shouldQueryTopologies() throws IOException {
        List<Topology> topologyList = api.queryTopologies();
        assertEquals(0, topologyList.size());
        for(int i=1; i<=5;i++){
            Path fileName = Path.of("./files/test topologies/topology_"+i+".json");
            Topology topology = api.readJSON(fileName);
        }
        topologyList = api.queryTopologies();
        assertEquals(5, topologyList.size());
    }

    @DisplayName("Delete Topology given topology id")
    @Test
    public void shouldDeleteTopologyGivenItsId() throws IOException {
        for(int i=1; i<=5;i++){
            Path fileName = Path.of("./files/test topologies/topology_"+i+".json");
            Topology topology = api.readJSON(fileName);
        }
        assertEquals(5,api.queryTopologies().size());
        api.deleteTopolgy("top1");
        assertEquals(4,api.queryTopologies().size());
        api.deleteTopolgy("top50");
        assertEquals(4,api.queryTopologies().size());

    }

    @DisplayName("Should get a list of devices given a topology id")
    @Test
    public void shouldGetListOfDevicesGivenTopologyId() throws IOException {
        for(int i=1; i<=5;i++){
            Path fileName = Path.of("./files/test topologies/topology_"+i+".json");
            Topology topology = api.readJSON(fileName);
        }
        List<Component> top3Components = new ArrayList<>();
        assertEquals(0,top3Components.size());
        top3Components = api.queryDevices("top3");
        assertEquals(3,top3Components.size());
        assertEquals("resistor", top3Components.get(0).getType());
        assertEquals("resistor", top3Components.get(1).getType());
        assertEquals("nmos", top3Components.get(2).getType());
    }

    @DisplayName("Gets list of devices using topology id and an id of a device")
    @Test
    public void shouldGetListOfDevicesGivenTopologyIdAndNetlistId() throws IOException {
        for(int i=1; i<=5;i++){
            Path fileName = Path.of("./files/test topologies/topology_"+i+".json");
            Topology topology = api.readJSON(fileName);
        }
        List<Component> listOfDevices = new ArrayList<>();
        assertEquals(0,listOfDevices.size());
        listOfDevices= api.queryDevicesWithNetlistNode("top3","res5");
        assertEquals(3,listOfDevices.size());

    }


}