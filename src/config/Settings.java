package config;


import twitter4j.conf.ConfigurationBuilder;



public class Settings {
    
    
    public static final String hostname = "localhost";
    public static final int portNumber = 20000; 
    
	public static final int keywordMax = 400;
	public static final int batchSize = 3000;
	public static int minNewFreq = 5;
	public static long timer = 10*60*1000;
	public static long sample = 60*1000+1;
	public static long pid = 1;
		
	public static String[] baseKeywords = {"london"};
	public static String[] blackList = {"bbc", "bbc2013", "cnn", "news", "twitter", "socialmedia", "rt","teamfollowback","follow"};
	
    
    public static ConfigurationBuilder getConfiguration(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("0yhMV2lOeldljwnF6zF39w");
        cb.setOAuthConsumerSecret("UvZ6nxXBp5KQCjccShSozmbJRMhum0U3BmjZqIlf4");
        cb.setOAuthAccessToken("1167411691-7tmlHBn39Z1QOPm5jovzHmwc4M3fhPpxrZVbXn3");
        cb.setOAuthAccessTokenSecret("yXkNOC1K8Hp35vqeSFJnaqd110FPpTlgwoXp9T8A");
        return cb;
    }
    
    
    
}
