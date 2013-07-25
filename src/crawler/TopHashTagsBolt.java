package crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableList;

import config.Settings;

import crawler.util.Item;

import storm.starter.tools.Rankable;
import storm.starter.tools.RankableObjectWithFields;
import storm.starter.tools.Rankings;
import storm.starter.util.TupleHelpers;
import backtype.storm.Config;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


public final class TopHashTagsBolt extends BaseBasicBolt {

    private static final long serialVersionUID = -1369800530256637409L;
    private static final Logger LOG = Logger.getLogger(TopHashTagsBolt.class);

    
    private int updateFilterFrequencyInSeconds;
    private int notifyRankingsFrequencyInSeconds;
    private int ticksToUpdateFilter;
    private int ticks;
    
    
    private int count;
    private Rankings rankings;
    
    private HashSet<String> filter;
	private String[] initialWords;
    
    
    public TopHashTagsBolt(int topN, int updateFilterFrequencyInSeconds, int notifyRankingsFrequencyInSeconds) {
    	if (topN < 1) {
            throw new IllegalArgumentException("topN must be >= 1 (you requested " + topN + ")");
        }
        if (updateFilterFrequencyInSeconds < 1) {
            throw new IllegalArgumentException("The emit frequency must be >= 1 seconds (you requested "
                + updateFilterFrequencyInSeconds + " seconds)");
        }
        this.count = topN;
        this.updateFilterFrequencyInSeconds = updateFilterFrequencyInSeconds;
        this.notifyRankingsFrequencyInSeconds  = notifyRankingsFrequencyInSeconds;
        this.ticksToUpdateFilter = updateFilterFrequencyInSeconds / notifyRankingsFrequencyInSeconds;
        this.ticks = 0;
        
        this.filter = new HashSet<String>();
        this.rankings = new Rankings(count);
        this.initialWords = Settings.baseKeywords;
 	   
        
    }
    
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        if (TupleHelpers.isTickTuple(tuple)) {
        	ticks++;
        	if (ticks>=ticksToUpdateFilter){
        		ticks = 0;
        		getLogger().info("Updating Twitter search filter");
                updateFilter(collector);
        	}
        	
        	collector.emit("ranking", new Values(rankings.getTopResults()));
            
        }
        else { //received new info
            updateRankingsWithTuple(tuple);
            
        }
    }
    
    private void updateFilter(BasicOutputCollector collector) {
       
        
        //We create the new filter to send to the crawler
        if (updateFilter()){
        	Values v = new Values();
            v.add(ImmutableList.copyOf(filter));
            
            collector.emit("filter",v);
        	
        }
        
        
        
    }
    
    /**
     * Recomputes hash search filter. 
     * @return True if filter has changed since last update
     */
    
    private boolean updateFilter() {
    	List<Rankable> items = rankings.getRankings();
    	HashSet<String> hashtags = new HashSet<String>();
    	
    	for (Rankable item: items) {
        	hashtags.add((String)item.getObject());
        }
    	
   
    	
    	for(String word: initialWords){
    		hashtags.add(word);
    	}
        
    	if(filter.equals(hashtags)){
    		return false;
    	} else {
    		filter = hashtags;
    		return true;
    	}
        
		
	}

	void updateRankingsWithTuple(Tuple tuple) {
        Rankable rankable = RankableObjectWithFields.from(tuple);
        rankings.updateWith(rankable);
    }
    
    Logger getLogger() {
        return LOG;
    }
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("ranking", new Fields("Rankings"));
        declarer.declareStream("filter", new Fields("Filter"));
    }
    
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, notifyRankingsFrequencyInSeconds);
        return conf;
    }
    
    
}
