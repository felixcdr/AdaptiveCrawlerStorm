package crawler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableList;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import config.Settings;

public class AdaptiveTwitterStreamBolt extends BaseRichBolt implements
		StatusListener {

	private static final long serialVersionUID = -752035335767518162L;
	OutputCollector collector;
	TopologyContext context;

	List<Status> tweets;

	  
	
	
	TwitterStream stream;

	private static final Logger LOG = Logger
			.getLogger(AdaptiveTwitterStreamBolt.class);

	
	@Override
	public void prepare(Map conf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.context = context;
		
		tweets = new ArrayList<Status>();
		ConfigurationBuilder cb = Settings.getConfiguration();
		stream = new TwitterStreamFactory(cb.build()).getInstance();
		stream.addListener(this);

		this.startFilterStream(Settings.baseKeywords);
	}

	@Override
	public void execute(Tuple tuple) {

		// Message from ticking spout, time to send stored tweets
		if (tuple.getValue(0) instanceof String) {

			while (!tweets.isEmpty()) {
				Status st = tweets.remove(0);
				collector.emit(new Values(st));
			}
			// received new filter, update crawler
		} else if (tuple.getValue(0) instanceof ImmutableList) {

			ImmutableList<String> list =(ImmutableList<String>)tuple.getValue(0);
			String[] filter = list.toArray(new String[list.size()]);
			getLogger().info("Received new filter: " + Arrays.toString(filter));
			
			if(filter.length > 0){
				startFilterStream(filter);
			}

		}
	}
	
	
	public void startFilterStream(String[] filter) {
		FilterQuery fq = new FilterQuery();
		fq.track(filter);
		stream.filter(fq);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tweet"));
	}

	// Twitter4j Status Listener from here//
	public void onStatus(Status status) {
		tweets.add(status);
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	}

	public void onScrubGeo(long userId, long upToStatusId) {
	}

	public void onException(Exception ex) {
	}

	public void onStallWarning(StallWarning sw) {
	}

	


	Logger getLogger() {
		return LOG;
	}

}
