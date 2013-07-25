package gui;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import crawler.util.CrawlerGUI;
import crawler.util.TopItems;

public class CrawlerGUIImpl implements CrawlerGUI.Iface {

	private ResultDisplay display;
	private int port;

	private CrawlerGUIImpl t;

	public CrawlerGUIImpl(int port, ResultDisplay display) {
		this.port = port;
		this.display = display;
		t = this;
	}

	@Override
	public void updateRankings(TopItems top) throws TException {

		display.updateTopList(top);
	}

	
	@Override
	public void updateCount(long count) throws TException {
		display.updateCount( count);
		
	}
	
	public void start() throws TTransportException {
		    	
		Runnable rb = new Runnable() {
            @Override
            public void run() {
		
    	CrawlerGUI.Processor processor = new CrawlerGUI.Processor(t);
    	TServerTransport serverTransport = null;
		try {
			serverTransport = new TServerSocket(port);
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
		
    	
    	System.out.println("Starting the simple server...");
    	server.serve();
    	 	
    	
            }
		};
    	Thread th = new Thread(rb);
        th.start();
    	
	}

	
}
