package edu.ucla.cs.jonnykong.cs239;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import edu.ucla.cs.jonnykong.cs239.CouchUpdateMapper;
import edu.ucla.cs.jonnykong.cs239.CouchUpdateBolt;


public class WordCountTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReaderSpout());
        // builder.setBolt("word-counter", new WordCountBolt(), 1).shuffleGrouping("word-reader");
        
        CouchUpdateBolt couch_update_bolt 
            = new CouchUpdateBolt("http://127.0.0.1:5984", "baseball", new CouchUpdateMapper());
        builder.setBolt("couch-updater", couch_update_bolt).shuffleGrouping("word-reader");

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