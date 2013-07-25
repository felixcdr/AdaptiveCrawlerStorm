package crawler;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;


public class AdaptiveTwitterTopology {
    
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        
    	
    	int smallWindow = 30;
    	int largeWindow = 5*60;
    	
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("tickSpout", new TickSpout(),1);
        
        builder.setBolt("tweetSpitter", new AdaptiveTwitterStreamBolt(), 1).shuffleGrouping("tickSpout").shuffleGrouping("topHashTags","filter");
        
        builder.setBolt("hashExtractor", new HashExtractorBolt(), 1).shuffleGrouping("tweetSpitter");
         //rolling parameters: 5 minutes sliding window, emits counts every 30 seconds, updates filter every five minutes
        builder.setBolt("hashCount", new RollingCountBolt(largeWindow, smallWindow), 1).fieldsGrouping("hashExtractor", "hashTags", new Fields("hashTag"));
        
        
        //top hashtag parameters: 10 entries in the top list, updates twitter filter every 5 minutes
        builder.setBolt("topHashTags", new TopHashTagsBolt(10,largeWindow,smallWindow), 1).fieldsGrouping("hashCount", new Fields("obj"));
        
        builder.setBolt("guiClient", new GUIClientBolt(), 1).shuffleGrouping("topHashTags","ranking").shuffleGrouping("hashExtractor","count");
        
        
        
        Config conf = new Config();
        //conf.setDebug(true);
        

        //If it was to be run on a cluster , uncomment the following code.
                    /*if (args != null && args.length > 0) {
         conf.setNumWorkers(2);
         StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
         /} else {*/
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("TweetSniffer", conf, builder.createTopology());
        Utils.sleep(1000000000);
        cluster.killTopology("TweetSniffer");
        cluster.shutdown();
        //}
    }
}
