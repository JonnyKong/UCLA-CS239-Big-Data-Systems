package edu.ucla.cs.jonnykong.cs239;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class WordCountTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReaderSpout());
        builder.setBolt("word-counter", new WordCountBolt(), 1).shuffleGrouping("word-reader");

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