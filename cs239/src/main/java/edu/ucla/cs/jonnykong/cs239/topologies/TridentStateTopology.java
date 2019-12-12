package edu.ucla.cs.jonnykong.cs239.topologies;

import edu.ucla.cs.jonnykong.cs239.mapper.CouchInsertMapper;
import edu.ucla.cs.jonnykong.cs239.trident.state.CouchState;
import edu.ucla.cs.jonnykong.cs239.trident.state.CouchStateFactory;
import edu.ucla.cs.jonnykong.cs239.trident.state.CouchStateUpdater;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.state.StateFactory;
import org.apache.storm.tuple.Fields;


public class TridentStateTopology {
    public static void main(String[] args) throws Exception {
        String url = "http://127.0.0.1:5984";
        String collectionName = "baseball";
        CouchInsertMapper mapper = new CouchInsertMapper();
        CouchState.Options options = new CouchState.Options()
                .withUrl(url)
                .withCollectionName(collectionName)
                .withMapper(mapper);

        DocInsertSpout spout = new DocInsertSpout();
        TridentTopology topology = new TridentTopology();
        Stream stream = topology.newStream("spout1", spout);

        StateFactory factory = new CouchStateFactory(options);
        stream.partitionPersist(factory, new Fields("word"),
                new CouchStateUpdater(), new Fields());

        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster localCluster = new LocalCluster();
        try {
            localCluster.submitTopology("trident", conf, topology.build());
            Thread.sleep(10000);
            localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            localCluster.shutdown();
        }
    }
}