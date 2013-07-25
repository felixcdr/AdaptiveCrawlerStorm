package crawler;

import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import config.Settings;
import storm.starter.util.TupleHelpers;
import twitter4j.HashtagEntity;
import twitter4j.Status;

/**
 * Extracts hashtags from Status, and submits every x seconds the total count of
 * tweets that have been processed.
 * 
 * The bolt also filters out tags that appear in the blacklist defined at settings
 * 
 * @author fcuadrado
 * 
 */

public class HashExtractorBolt extends BaseRichBolt {

	private OutputCollector collector;

	private long tweetCount = 0;

	private Set<String> blacklist;
	
	
	private int UPDATE_TWEET_COUNT_FREQUENCY_SECS = 1;

	@Override
	public void prepare(Map conf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		blacklist = new HashSet<String>();
		blacklist.addAll(Arrays.asList(Settings.blackList));
	}

	@Override
	public void execute(Tuple tuple) {

		if (TupleHelpers.isTickTuple(tuple)) {

			collector.emit("count", new Values(tweetCount));

		} else {
			tweetCount++;

			Status tweet = (Status) tuple.getValue(0);
			HashtagEntity[] tags = tweet.getHashtagEntities();
			if (tags != null) {
				for (HashtagEntity tag : tags) {
					String hashTag = tag.getText();
					if (!blacklist.contains(hashTag))
						collector.emit("hashTags", new Values(hashTag));
				}
			}
		}
		collector.ack(tuple);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream("hashTags", new Fields("hashTag"));
		declarer.declareStream("count", new Fields("count"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS,
				UPDATE_TWEET_COUNT_FREQUENCY_SECS);
		return conf;
	}

}
