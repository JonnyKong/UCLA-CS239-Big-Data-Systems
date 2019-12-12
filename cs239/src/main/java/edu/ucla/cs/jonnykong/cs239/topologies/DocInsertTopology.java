package edu.ucla.cs.jonnykong.cs239.topologies;

import edu.ucla.cs.jonnykong.cs239.bolt.CouchInsertBolt;
import edu.ucla.cs.jonnykong.cs239.mapper.CouchInsertMapper;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class DocInsertTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new DocInsertSpout());
        // builder.setBolt("word-counter", new WordCountBolt(), 1).shuffleGrouping("word-reader");

        CouchInsertBolt couchInsertBolt = new CouchInsertBolt("http://127.0.0.1:5984", "baseball", new CouchInsertMapper());
        builder.setBolt("couch-inserter", couchInsertBolt).shuffleGrouping("word-reader");

        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster localCluster = new LocalCluster();
        try {
            localCluster.submitTopology("wordcounter-topology", conf, builder.createTopology());
            Thread.sleep(10000);
            localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            localCluster.shutdown();
        }
    }
}