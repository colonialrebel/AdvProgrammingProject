package eventObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventReader {

	private List<EventObject> morning = new ArrayList<EventObject>();
	private List<EventObject> midday = new ArrayList<EventObject>();
	private List<EventObject> afternoon = new ArrayList<EventObject>();
	private List<EventObject> evening = new ArrayList<EventObject>();
	
	public static final String[] EVENT_TITLE_LIST = {"Go get coffee"};

	public EventReader() {
		
	}
	
	public List<List<EventObject>> buildEvent(String filename) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
		String line1 = "";		
		String line2 = "";
		String line3 = "";


		int counter = 0;
		
		while (line1 != null) {

			line1 = reader.readLine();
			line2 = reader.readLine();
			line3 = reader.readLine();
			List<String> separated1 = new ArrayList<String>();
			List<String> separated2 = new ArrayList<String>();
			List<String> separated3 = new ArrayList<String>();

			if (line3!= null) {	
				
				separated1 = Arrays.asList(line1.split("\\s*,\\s*"));
				separated2 = Arrays.asList(line2.split("\\s*,\\s*"));
				separated3 = Arrays.asList(line3.split("\\s*,\\s*"));
				String tempTime = separated1.get(7);
				//System.out.println(tempTime);   -> prints time of day 0 is morning, 1 is midday, 2 is afternoon, 3 is evening
				EventObject eo = new EventObject(
							separated1.get(0), 
							separated1.get(1), 
							trimArray(separated1), 
							trimArray(separated2), 
							trimArray(separated3));
				
				
				if (tempTime.equals("0")) {
					morning.add(eo);
				} else if (tempTime.equals("1")) {
					midday.add(eo);
				} else if (tempTime.equals("2")) {
					afternoon.add(eo);
				} else if (tempTime.equals("3")) {
					evening.add(eo);
				}
				counter++;
			}
		
			
		}
		
		reader.close();
		List<List<EventObject>> list = new ArrayList<List<EventObject>>(4);
		list.add(morning);
		list.add(midday);
		list.add(afternoon);
		list.add(evening);
		
		return list;
	}

	private String[] trimArray(List<String> list) {  
		/*
		 * This method turns an array list into a string array
		 * We tried converting an ArrayList<String> to a normal array but ended up with a weird scenario where the created array 
		 * was not of type String and couldn't be cast and thus this method exists
		*/
		String[] arr = new String[] {list.get(2), list.get(3), list.get(4), list.get(5), list.get(6),list.get(8)};
		
		return arr;
		
	}
	


}
