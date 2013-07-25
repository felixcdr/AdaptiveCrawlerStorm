package crawler;


import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.google.common.collect.ImmutableMap;

import storm.starter.tools.Rankings;
import storm.starter.util.TupleHelpers;
import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import config.Settings;
import crawler.util.CrawlerGUI;
import crawler.util.CrawlerGUI.Client;
import crawler.util.Item;
import crawler.util.ThriftHelper;
import crawler.util.TopItems;

public class GUIClientBolt extends BaseRichBolt{

    private OutputCollector collector;
 	private Client client;

	private final int UPDATE_GUI_FREQUENCY_SECS = 1;
	
	
	
	private static final Logger LOG = Logger.getLogger(GUIClientBolt.class);
	
    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        try {
            this.collector = collector;
            connect();
        } catch (UnknownHostException ex) {
        	getLogger().error("", ex);
        } catch (TTransportException ex) {
			getLogger().error("", ex);
		}
    }

    private void connect() throws UnknownHostException, TTransportException {
      
    	TTransport transport = new TSocket(Settings.hostname, Settings.portNumber);
    	transport.open();

    	TProtocol protocol = new  TBinaryProtocol(transport);
    	client = new CrawlerGUI.Client(protocol);
    }

    @Override
    public void execute(Tuple tuple) {
        try {
        	
        	 if (tuple.getSourceStreamId().equals("count") ) {
                 
                 client.updateCount(tuple.getLong(0));
                 
             }
        	
   
        	 if (tuple.getValue(0) instanceof ImmutableMap) {
        		 
        		ImmutableMap<String,Long> rankings = (ImmutableMap<String,Long>)tuple.getValue(0);
        		
        		
        		TopItems topItems = ThriftHelper.prepareTopItems(rankings);
        		getLogger().debug("Received Rankings: " + topItems);
        		client.updateRankings(topItems);
        		
                
                
                
            } 

            collector.ack(tuple);
          
        }catch (TException ex) {
            getLogger().error("Problem sending Object through Thrift", ex);
        }
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tagFilter"));
    }
 

    Logger getLogger() {
        return LOG;
    }
    
}
