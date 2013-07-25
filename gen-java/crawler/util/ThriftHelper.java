package crawler.util;

import java.util.ArrayList;

import com.google.common.collect.ImmutableMap;


public class ThriftHelper {
	public static TopItems prepareTopItems(ImmutableMap<String,Long> rankings){
		
		ArrayList<Item> items = new ArrayList<Item>();
		for (String hashtag : rankings.keySet()) {
			items.add(new Item(rankings.get(hashtag),hashtag));
		}
		
		return new TopItems(items);
		
	}
}
