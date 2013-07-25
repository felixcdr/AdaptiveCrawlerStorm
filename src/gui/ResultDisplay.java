package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.apache.thrift.transport.TTransportException;
import org.jfree.data.UnknownKeyException;
import org.jfree.ui.RefineryUtilities;

import crawler.TopHashTagsBolt;
import crawler.util.CrawlerGUI;
import crawler.util.TopItems;

import storm.starter.tools.Rankings;
import twitter4j.Status;

public class ResultDisplay extends JFrame implements KeyListener {

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel bottomPanel;
    private JLabel programName;
    private JLabel hashLabel;
    private PieChart topTags;
    private PieChart topLocations;
    private JTextField filter;
    private JLabel tweetCount;
    private JTextArea tweets;
    private JPanel tweetsPanel;
    private Color twitterCol;
	private CrawlerGUIImpl server;
    private static String filterToSend = "";
    private static boolean filterChanged = false;

    
    private static final Logger LOG = Logger.getLogger(ResultDisplay.class);
    
    public ResultDisplay(final String title) throws IOException, ClassNotFoundException, TTransportException {
        super(title);
        setSize(new Dimension(700, 700));
        twitterCol = new Color(150, 201, 255);
      
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(twitterCol);
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(twitterCol);
        midPanel = new JPanel(new FlowLayout());
        midPanel.setBackground(twitterCol);
        bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.setBackground(twitterCol);

        JPanel tempContainer = new JPanel(new GridLayout(3, 1));
        tempContainer.setBackground(twitterCol);


        programName = new JLabel("TweetSniffer v0.1");
        hashLabel = new JLabel("Enter #Tag:");
        tweetCount = new JLabel("Tweets Processed: 0");
        Font ft = new Font("Calibri", Font.BOLD, 12);
        hashLabel.setFont(ft);
        programName.setFont(ft);
        tweetCount.setFont(ft);


        filter = new JTextField("Enter HashTag Filter");
        filter.addKeyListener(this);
        tweetsPanel = new JPanel();
        tweetsPanel.setBackground(twitterCol);


        tweets = new JTextArea();
        tweets.setRows(10);
        tweets.setColumns(70);
        tweets.setWrapStyleWord(true);
        tweets.setText("");
        JScrollPane scroll = new JScrollPane(tweets);
        tweetsPanel.add(scroll);
        RefineryUtilities.centerFrameOnScreen(this);

        Color chartColor = new Color(209, 231, 255);
        topTags = new PieChart("Top Tags", chartColor, new Dimension(200, 300));
        topLocations = new PieChart("Top Locations", chartColor, new Dimension(200, 300));


        RefineryUtilities.centerFrameOnScreen(this);
        
        server = new CrawlerGUIImpl(20000, this);
        
        tempContainer.add(programName);
        tempContainer.add(tweetCount);
        topPanel.add(tempContainer, BorderLayout.LINE_START);

        JPanel tagPanel = new JPanel(new FlowLayout());
        tagPanel.setBackground(twitterCol);
        hashLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        tagPanel.add(hashLabel);
        tagPanel.add(filter);
        topPanel.add(tagPanel, BorderLayout.CENTER);

        midPanel.add(tweetsPanel);

        bottomPanel.add(topTags.getChart());
        bottomPanel.add(topLocations.getChart());

        mainPanel.add(topPanel, BorderLayout.PAGE_START);
        mainPanel.add(midPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        tweetCount.setForeground(new Color(255, 0, 0, 180));

        tweets.setFont(new Font("Calibri", Font.BOLD, 14));
        tweets.setForeground(Color.WHITE);
        tweets.setBackground(twitterCol);


        //listener.startServer();
        
        server.start();
        
        
        filter.setPreferredSize(new Dimension(200, 30));


        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        setVisible(true);
        filter.setMinimumSize(new Dimension(200, 40));
        filter.setText("");

    }

    public static boolean checkFilterChanged() {
        return filterChanged;
    }
    /**
    public static ArrayList<ResultTuple<String, Integer>> getTestData() {
        ArrayList<ResultTuple<String, Integer>> results = new ArrayList<ResultTuple<String, Integer>>();
        for (int i = 0; i < 10; i++) {
            results.add(new ResultTuple<String, Integer>("Test" + i, i));
        }
        return results;
    }

    
    
    public void processResults(DataSet resultSet) {
        try {
            if (resultSet.getDataDescription().equals("Top Hash Tags")) {
                topTags.updateData(resultSet.getResults());
            } else if (resultSet.getDataDescription().equals("Top 10 Locations")) {
                topLocations.updateData(resultSet.getResults());
            } else if (resultSet.getDataDescription().equals("Tweet")) {
                Status tweet = resultSet.getTweetResult();
                tweets.setText("[Tweet] " + tweet.getText() + " [/Tweet]\n" + tweets.getText());
            } else if (resultSet.getDataDescription().equals("TweetCount")) {
                tweetCount.setText("Tweets Processed: " + resultSet.getIntegerResult());
            }
        } catch (UnknownKeyException uke) {
        }
    }
    */

    public void updateTopList(TopItems top) {
    	getLogger().info("Received Rankings: " + top);
    	topTags.updateData(top.getElements());
       
    }
    
    
    public void updateCount(long count) {
    	
    	tweetCount.setText("Tweets Processed: " + count);
    	
    }
    
       
    
    public static String getFilterToSend() {
        return filterToSend;
    }

    public static void clearFilterToSend() {
        filterToSend = "";
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, TTransportException {
        ResultDisplay display = new ResultDisplay("Twitter Processor Results");
    }

    public void updateTweetCount(int n) {
        tweetCount.setText("Tweets Processed: " + n);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getSource() == filter) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                filterToSend = ((JTextField) e.getSource()).getText().trim();
                if(!filterToSend.equalsIgnoreCase("")){
                    filterChanged = true;
                }
            }
        }
    }
    
    public static void setFilterChanged(boolean value){
        filterChanged = value;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    Logger getLogger() {
        return LOG;
    }

}